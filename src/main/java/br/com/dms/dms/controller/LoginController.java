package br.com.dms.dms.controller;

import br.com.dms.dms.model.LoginModel;
import br.com.dms.dms.repository.LoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final LoginRepository loginRepository;

    public LoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @GetMapping("/login")
    public String telaLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String usuario,
                             @RequestParam String senha,
                             HttpSession session,
                             Model model) {

        LoginModel user = loginRepository.findByDsUsuarioAndDsSenha(usuario, senha);

        if (user == null) {
            model.addAttribute("erro", "Usuário ou senha inválidos");
            return "login";
        }

        session.setAttribute("usuarioLogado", user.getDsUsuario());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}