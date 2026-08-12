package br.com.dms.dms.controller;

import br.com.dms.dms.model.LoginModel;
import br.com.dms.dms.repository.LoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UsersController {

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/usuarios")
    public String usuarios(Model model, HttpSession session) {
        List<LoginModel> listaUsuarios = loginRepository.findAll();
        model.addAttribute("usuarios", listaUsuarios);

        String usuarioLogado = (String) session.getAttribute("usuarioLogado");
        boolean isAdmin = "Admin".equalsIgnoreCase(usuarioLogado);

        model.addAttribute("isAdmin", isAdmin);

        return "usuarios";
    }

    @GetMapping("/deletar-usuario/{id}")
    public String deletarUsuario(@PathVariable Long id, HttpSession session) {
        String usuarioLogado = (String) session.getAttribute("usuarioLogado");
        if (!"Admin".equalsIgnoreCase(usuarioLogado)) {
            return "redirect:/usuarios?erro=sem_permissao";
        }

        if (id == 1L) {
            return "redirect:/usuarios?erro=admin";
        }

        loginRepository.deleteById(id);
        return "redirect:/usuarios";
    }

    @PostMapping("/alterar-senha-usuario")
    @ResponseBody
    public Map<String, Object> alterarSenhaUsuario(@RequestBody Map<String, String> dados, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            String usuarioLogado = (String) session.getAttribute("usuarioLogado");
            if (!"Admin".equalsIgnoreCase(usuarioLogado)) {
                response.put("sucesso", false);
                response.put("mensagem", "Apenas o Administrador pode alterar senhas.");
                return response;
            }

            Long id = Long.parseLong(dados.get("id"));
            String novaSenha = dados.get("novaSenha");

            LoginModel usuario = loginRepository.findById(id).orElse(null);

            if (usuario == null) {
                response.put("sucesso", false);
                response.put("mensagem", "Usuário não encontrado no sistema.");
                return response;
            }

            usuario.setDsSenha(novaSenha);
            loginRepository.save(usuario);

            response.put("sucesso", true);

        } catch (Exception e) {
            response.put("sucesso", false);
            response.put("mensagem", "Erro interno no servidor.");
        }

        return response;
    }

    @PostMapping("/criar-usuario")
    @ResponseBody
    public Map<String, Object> criarUsuario(@RequestBody Map<String, String> dados, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            String nomeUsuario = dados.get("usuario");
            String senha = dados.get("senha");

            LoginModel usuarioExistente = loginRepository.findByDsUsuario(nomeUsuario);

            if (usuarioExistente != null) {
                response.put("sucesso", false);
                response.put("mensagem", "Este nome de usuário já está em uso. Escolha outro.");
                return response;
            }

            String criador = (String) session.getAttribute("usuarioLogado");
            if (criador == null || criador.isEmpty()) {
                criador = "Sistema";
            }

            String dataCriacao = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            LoginModel novoUsuario = new LoginModel();
            novoUsuario.setDsUsuario(nomeUsuario);
            novoUsuario.setDsSenha(senha);
            novoUsuario.setDtCriacao(dataCriacao);
            novoUsuario.setDsCriador(criador);

            loginRepository.save(novoUsuario);

            response.put("sucesso", true);

        } catch (Exception e) {
            response.put("sucesso", false);
            response.put("mensagem", "Erro interno no servidor.");
        }

        return response;
    }
}