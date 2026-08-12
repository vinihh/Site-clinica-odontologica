package br.com.dms.dms.controller;

import br.com.dms.dms.model.*;
import br.com.dms.dms.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CadPacCttController {

    @Autowired
    private EndPacRepository enderecoRepository;

    @Autowired
    private PacRepository pacRepository;

    @Autowired
    private TelPacRepository telefoneRepository;

    @Autowired
    private EmailPacRepository emailRepository;

    @RequestMapping("/cad-pac-ctt")
    public String cadPacCtt(HttpSession session) {
        Long idPaciente = (Long) session.getAttribute("idPaciente");
        if (idPaciente == null) {
            return "redirect:/pacientes";
        }
        return "cad-pac-ctt";
    }

    @PostMapping("/cancelar-cadastro")
    @ResponseBody
    public void cancelarCadastro(HttpSession session) {
        Long id = (Long) session.getAttribute("idPaciente");
        if (id != null) {
            pacRepository.deleteById(id);
            session.removeAttribute("idPaciente");
        }
    }

    @PostMapping("/salvar-contato")
    public String salvarContato(CadPacCttModel contato,
                                @RequestParam(required = false) String dsEmail2,
                                @RequestParam(required = false) String dsTelefone2,
                                Model model, HttpSession session) {

        try {
            Long idPaciente = (Long) session.getAttribute("idPaciente");

            if (idPaciente == null) {
                return "redirect:/pacientes";
            }

            PacModel paciente = pacRepository.findById(idPaciente)
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

            if (contato.getNmRua() == null || contato.getNmRua().trim().isEmpty() ||
                    contato.getDsBairro() == null || contato.getDsBairro().trim().isEmpty() ||
                    contato.getDsCidade() == null || contato.getDsCidade().trim().isEmpty() ||
                    contato.getDsUf() == null || contato.getDsUf().trim().isEmpty() ||
                    contato.getDsCep() == null || contato.getDsCep().trim().isEmpty() ||
                    contato.getDsTelefone() == null || contato.getDsTelefone().trim().isEmpty() ||
                    contato.getDsEmail() == null || contato.getDsEmail().trim().isEmpty()) {

                model.addAttribute("erro", "Preencha todos os campos!");
                return "cad-pac-ctt";
            }

            EndPacModel end = new EndPacModel();
            end.setNmRua(contato.getNmRua());
            end.setDsBairro(contato.getDsBairro());
            end.setDsCidade(contato.getDsCidade());
            end.setDsUf(contato.getDsUf());
            end.setDsNumero(contato.getDsNumero());
            end.setDsComplemento(contato.getDsComplemento());
            end.setDsCep(contato.getDsCep());
            end.setIdPaciente(paciente);
            enderecoRepository.save(end);

            TelPacModel tel = new TelPacModel();
            tel.setDsTelefone(contato.getDsTelefone());
            tel.setIdPaciente(paciente);
            telefoneRepository.save(tel);

            if (dsTelefone2 != null && !dsTelefone2.trim().isEmpty()) {
                TelPacModel tel2 = new TelPacModel();
                tel2.setDsTelefone(dsTelefone2);
                tel2.setIdPaciente(paciente);
                telefoneRepository.save(tel2);
            }

            EmailPacModel email = new EmailPacModel();
            email.setDsEmail(contato.getDsEmail());
            email.setIdPaciente(paciente);
            emailRepository.save(email);

            if (dsEmail2 != null && !dsEmail2.trim().isEmpty()) {
                EmailPacModel email2 = new EmailPacModel();
                email2.setDsEmail(dsEmail2);
                email2.setIdPaciente(paciente);
                emailRepository.save(email2);
            }

            String usuarioLogado = (String) session.getAttribute("usuarioLogado");
            paciente.setUsuarioAtualizacao(usuarioLogado != null ? usuarioLogado : "Sistema");
            paciente.setDataHoraAtualizacao(java.time.LocalDateTime.now());
            pacRepository.save(paciente);

            session.removeAttribute("idPaciente");

            return "redirect:/pacientes";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar contato!");
            return "cad-pac-ctt";
        }
    }
}