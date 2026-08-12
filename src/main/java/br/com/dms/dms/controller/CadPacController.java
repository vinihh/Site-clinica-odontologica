package br.com.dms.dms.controller;

import br.com.dms.dms.model.PacModel;
import br.com.dms.dms.repository.PacRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Controller
public class CadPacController {

    @Autowired
    private PacRepository pacRepository;

    @RequestMapping("/cad-pac")
    public String cadPacCtt() {
        return "cad-pac";
    }

    @PostMapping("/salvar-pac")
    public String salvarPaciente(@ModelAttribute("paciente") PacModel paciente,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        try {

            if (paciente.getNmPaciente() == null || paciente.getNmPaciente().isEmpty() ||
                    paciente.getDsCpf() == null || paciente.getDsCpf().isEmpty() ||
                    paciente.getDsRg() == null || paciente.getDsRg().isEmpty() ||
                    paciente.getDtNascimento() == null || paciente.getDtNascimento().isEmpty()) {

                throw new IllegalArgumentException("Preencha todos os campos!");
            }
            if (!paciente.getDsCpf().matches("^[0-9.\\s-]+$")) {
                throw new IllegalArgumentException("O campo CPF deve conter apenas números!");
            }

            if (!paciente.getDsRg().matches("^[0-9.\\s-]+$")) {
                throw new IllegalArgumentException("O campo RG deve conter apenas números!");
            }

            if (!validarCPF(paciente.getDsCpf())) {
                throw new IllegalArgumentException("CPF inválido!");
            }

            if (!validarIdade(paciente.getDtNascimento())) {
                throw new IllegalArgumentException("Data de nascimento Inválida!");
            }

            if (pacRepository.existsByDsCpf(paciente.getDsCpf())) {
                throw new IllegalArgumentException("CPF já cadastrado!");
            }

            if (pacRepository.existsByDsRg(paciente.getDsRg())) {
                throw new IllegalArgumentException("RG já cadastrado!");
            }

            String usuarioLogado = (String) session.getAttribute("usuarioLogado");
            paciente.setUsuarioAtualizacao(usuarioLogado != null ? usuarioLogado : "Sistema");
            paciente.setDataHoraAtualizacao(java.time.LocalDateTime.now());

            pacRepository.save(paciente);

            session.setAttribute("idPaciente", paciente.getIdPaciente());

            return "redirect:/cad-pac-ctt";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            redirectAttributes.addFlashAttribute("paciente", paciente);

            return "redirect:/cad-pac";
        }
    }

    @GetMapping("/cad-pac")
    public String telaCadastroPaciente(Model model, HttpSession session) {
        Long idAbandonado = (Long) session.getAttribute("idPaciente");
        if (idAbandonado != null) {
            try {
                pacRepository.deleteById(idAbandonado);
            } catch (Exception e) {
            }
            session.removeAttribute("idPaciente");
        }

        if (!model.containsAttribute("paciente")) {
            model.addAttribute("paciente", new PacModel());
        }
        return "cad-pac";

    }

    public boolean validarCPF(String cpf) {
        if (cpf == null) return false;
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11) return false;
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0;
            int peso = 10;
            for (int i = 0; i < 9; i++) {
                int num = cpf.charAt(i) - '0';
                soma += num * peso--;
            }
            int resto = 11 - (soma % 11);
            int digito1 = (resto >= 10) ? 0 : resto;

            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                int num = cpf.charAt(i) - '0';
                soma += num * peso--;
            }
            resto = 11 - (soma % 11);
            int digito2 = (resto >= 10) ? 0 : resto;

            return digito1 == (cpf.charAt(9) - '0') && digito2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validarIdade(String dataNascimento) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate nascimento = LocalDate.parse(dataNascimento, formatter);
            LocalDate hoje = LocalDate.now();
            int static_idade = Period.between(nascimento, hoje).getYears();
            return static_idade >= 3 && static_idade <= 100;
        } catch (Exception e) {
            return false;
        }
    }
}