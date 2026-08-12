package br.com.dms.dms.controller;

import br.com.dms.dms.model.CadEspecModel;
import br.com.dms.dms.model.EspecialidadesModel;
import br.com.dms.dms.repository.EspecRepository;
import br.com.dms.dms.repository.EspecialidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EspecController {

    @Autowired
    private EspecialidadesRepository especialidadeRepository;

    @Autowired
    private EspecRepository funcionarioRepository;

    @GetMapping("/funcionarios")
    public String listarEspecialistas(
            @RequestParam(value = "nome", required = false) String nome,
            Model model) {

        List<CadEspecModel> especialistas;

        if (nome != null && !nome.isEmpty()) {
            especialistas = funcionarioRepository.findByNmFuncionarioContainingIgnoreCase(nome);
        } else {
            especialistas = funcionarioRepository.findAll();
        }

        especialistas.forEach(esp -> {
            List<EspecialidadesModel> lista = especialidadeRepository.findByFuncionarioIdFuncionario(esp.getIdFuncionario());

            String texto = lista.stream()
                    .map(EspecialidadesModel::getDsEspecialidade)
                    .collect(Collectors.joining(","));

            esp.setEspecialidadesTexto(texto);
        });

        model.addAttribute("listaEspecialidades", List.of(
                "Limpeza dental", "Clareamento dental", "Aparelho dentário", "Canal",
                "Implante dentário", "Prótese dentária", "Extração de dente", "Dente do siso"
        ));

        model.addAttribute("especialistas", especialistas);
        return "funcionarios";
    }

    @GetMapping("/deletar-funcionario/{id}")
    public String deletarEspecialista(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/funcionarios";
    }

    @PostMapping("/editar-funcionario")
    @ResponseBody
    @Transactional
    public void editarEspecialista(@RequestBody Map<String, Object> dados, jakarta.servlet.http.HttpSession session) { // <-- Sessão adicionada aqui
        Long id = Long.parseLong(dados.get("id").toString());
        CadEspecModel especialista = funcionarioRepository.findById(id).orElseThrow();

        especialista.setNmFuncionario(dados.get("nome").toString());
        especialista.setDsCpf(dados.get("cpf").toString());
        especialista.setDtNascimento(dados.get("nascimento").toString());
        especialista.setDsCro(dados.get("cro").toString());
        especialista.setDsEmail(dados.get("email").toString());
        especialista.setDsTelefone(dados.get("telefone").toString());

        String usuarioLogado = (String) session.getAttribute("usuarioLogado");
        especialista.setUsuarioAtualizacao(usuarioLogado != null ? usuarioLogado : "Sistema");
        especialista.setDataHoraAtualizacao(java.time.LocalDateTime.now());

        funcionarioRepository.save(especialista);

        List<EspecialidadesModel> especialidadesAntigas = especialidadeRepository.findByFuncionarioIdFuncionario(id);
        especialidadeRepository.deleteAll(especialidadesAntigas);

        List<String> novasEspecialidades = (List<String>) dados.get("especialidades");
        if (novasEspecialidades != null) {
            for (String dsEsp : novasEspecialidades) {
                EspecialidadesModel esp = new EspecialidadesModel();
                esp.setDsEspecialidade(dsEsp);
                esp.setFuncionario(especialista);
                especialidadeRepository.save(esp);
            }
        }
    }
}