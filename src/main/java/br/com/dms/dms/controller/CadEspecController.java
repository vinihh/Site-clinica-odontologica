package br.com.dms.dms.controller;

import br.com.dms.dms.model.CadEspecModel;
import br.com.dms.dms.model.EspecialidadesModel;
import br.com.dms.dms.repository.EspecRepository;
import br.com.dms.dms.repository.EspecialidadesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class CadEspecController {

    private final EspecRepository funcionarioRepository;
    private final EspecialidadesRepository especialidadeRepository;

    public CadEspecController(EspecRepository funcionarioRepository, EspecialidadesRepository especialidadeRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @GetMapping("/cad-funcionario")
    public String telaCadastro(Model model) {
        if (!model.containsAttribute("funcionario")) {
            model.addAttribute("funcionario", new CadEspecModel());
        }

        model.addAttribute("listaEspecialidades", List.of(
                "Limpeza dental", "Clareamento dental", "Aparelho dentário", "Canal",
                "Implante dentário", "Prótese dentária", "Extração de dente", "Dente do siso"
        ));
        return "cad-espec";
    }

    @PostMapping("/salvar-funcionario")
    public String salvarFuncionario(
            @ModelAttribute CadEspecModel funcionario,
            @RequestParam(value = "especialidade", required = false) List<String> especialidadesSelecionadas,
            jakarta.servlet.http.HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            validarCampos(funcionario);

            if (especialidadesSelecionadas == null || especialidadesSelecionadas.isEmpty()) {
                throw new IllegalArgumentException("Selecione ao menos uma especialidade!");
            }

            if (!validarCPF(funcionario.getDsCpf())) throw new IllegalArgumentException("CPF inválido!");
            if (funcionarioRepository.existsByDsCpf(funcionario.getDsCpf())) throw new IllegalArgumentException("CPF já cadastrado!");
            if (funcionarioRepository.existsByDsCro(funcionario.getDsCro())) throw new IllegalArgumentException("CRO já cadastrado!");
            if (!validarIdade(funcionario.getDtNascimento())) throw new IllegalArgumentException("Idade fora do permitido (3-100 anos)!");

            String usuarioLogado = (String) session.getAttribute("usuarioLogado");
            funcionario.setUsuarioAtualizacao(usuarioLogado != null ? usuarioLogado : "Sistema");
            funcionario.setDataHoraAtualizacao(java.time.LocalDateTime.now());

            CadEspecModel salvo = funcionarioRepository.save(funcionario);

            for (String dsEsp : especialidadesSelecionadas) {
                EspecialidadesModel esp = new EspecialidadesModel();
                esp.setDsEspecialidade(dsEsp);
                esp.setFuncionario(salvo);
                especialidadeRepository.save(esp);
            }

            redirectAttributes.addFlashAttribute("sucesso", true);
            return "redirect:/cad-funcionario";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            redirectAttributes.addFlashAttribute("funcionario", funcionario);
            return "redirect:/cad-funcionario";
        }
    }

    private void validarCampos(CadEspecModel f) {
        if (f.getNmFuncionario().isEmpty() || f.getDsCpf().isEmpty() || f.getDsCro().isEmpty() || f.getDsTelefone().isEmpty() || f.getDsEmail().isEmpty()) {
            throw new IllegalArgumentException("Preencha todos os campos obrigatórios!");
        }
    }

    public boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        return cpf.length() == 11 && !cpf.matches("(\\d)\\1{10}");
    }

    public boolean validarIdade(String dt) {
        try {
            LocalDate nasc = LocalDate.parse(dt, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int idade = Period.between(nasc, LocalDate.now()).getYears();
            return idade >= 3 && idade <= 100;
        } catch (Exception e) { return false; }
    }
}