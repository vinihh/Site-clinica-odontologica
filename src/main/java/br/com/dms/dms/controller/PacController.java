package br.com.dms.dms.controller;

import br.com.dms.dms.model.EmailPacModel;
import br.com.dms.dms.model.PacModel;
import br.com.dms.dms.model.TelPacModel;
import br.com.dms.dms.model.EndPacModel;
import br.com.dms.dms.repository.EmailPacRepository;
import br.com.dms.dms.repository.PacRepository;
import br.com.dms.dms.repository.TelPacRepository;
import br.com.dms.dms.repository.EndPacRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Controller
public class PacController {

    @Autowired
    private PacRepository pacRepository;

    @Autowired
    private EmailPacRepository emailRepository;

    @Autowired
    private TelPacRepository telefoneRepository;

    @Autowired
    private EndPacRepository enderecoRepository;

    public PacController(PacRepository pacRepository) {
        this.pacRepository = pacRepository;
    }

    @GetMapping("/pacientes")
    public String listarPacientes(
            @RequestParam(value = "nome", required = false) String nome,
            Model model) {

        List<PacModel> pacientes;

        if (nome != null && !nome.isEmpty()) {
            pacientes = pacRepository.findByNmPacienteContainingIgnoreCase(nome);
        } else {
            pacientes = pacRepository.findAll();
        }

        model.addAttribute("pacientes", pacientes);
        return "pacientes";
    }

    @GetMapping("/deletar-paciente/{id}")
    public String deletarPaciente(@PathVariable Long id) {
        pacRepository.deleteById(id);
        return "redirect:/pacientes";
    }

    @PostMapping("/editar-paciente")
    @ResponseBody
    public void editarPaciente(@RequestBody Map<String, String> dados, jakarta.servlet.http.HttpSession session) { // <-- 1. Sessão adicionada aqui

        Long id = Long.parseLong(dados.get("id"));
        PacModel paciente = pacRepository.findById(id).orElseThrow();

        paciente.setNmPaciente(dados.get("nome"));
        paciente.setDsCpf(dados.get("cpf"));
        paciente.setDsRg(dados.get("rg"));
        paciente.setDtNascimento(dados.get("nascimento"));

        String usuarioLogado = (String) session.getAttribute("usuarioLogado");
        paciente.setUsuarioAtualizacao(usuarioLogado != null ? usuarioLogado : "Sistema");
        paciente.setDataHoraAtualizacao(java.time.LocalDateTime.now());

        pacRepository.save(paciente);


        List<EmailPacModel> emails = paciente.getEmails();
        List<TelPacModel> telefones = paciente.getTelefones();
        List<EndPacModel> enderecos = paciente.getEnderecos();

        if (dados.get("email") != null && !dados.get("email").isEmpty()) {
            EmailPacModel email;
            if (emails != null && emails.size() > 0) {
                email = emails.get(0);
            } else {
                email = new EmailPacModel();
                email.setIdPaciente(paciente);
            }
            email.setDsEmail(dados.get("email"));
            emailRepository.save(email);
        }

        if (dados.get("email2") != null && !dados.get("email2").isEmpty()) {
            EmailPacModel email;
            if (emails != null && emails.size() > 1) {
                email = emails.get(1);
            } else {
                email = new EmailPacModel();
                email.setIdPaciente(paciente);
            }
            email.setDsEmail(dados.get("email2"));
            emailRepository.save(email);
        }

        if (dados.get("telefone") != null && !dados.get("telefone").isEmpty()) {
            TelPacModel tel;
            if (telefones != null && telefones.size() > 0) {
                tel = telefones.get(0);
            } else {
                tel = new TelPacModel();
                tel.setIdPaciente(paciente);
            }
            tel.setDsTelefone(dados.get("telefone"));
            telefoneRepository.save(tel);
        }

        if (dados.get("telefone2") != null && !dados.get("telefone2").isEmpty()) {
            TelPacModel tel;
            if (telefones != null && telefones.size() > 1) {
                tel = telefones.get(1);
            } else {
                tel = new TelPacModel();
                tel.setIdPaciente(paciente);
            }
            tel.setDsTelefone(dados.get("telefone2"));
            telefoneRepository.save(tel);
        }

        if (dados.get("cep") != null && !dados.get("cep").isEmpty()) {
            EndPacModel endereco;

            if (enderecos != null && enderecos.size() > 0) {
                endereco = enderecos.get(0);
            } else {
                endereco = new EndPacModel();
                endereco.setIdPaciente(paciente);
            }

            endereco.setDsCep(dados.get("cep"));
            endereco.setNmRua(dados.get("rua"));
            endereco.setDsBairro(dados.get("bairro"));
            endereco.setDsCidade(dados.get("cidade"));
            endereco.setDsUf(dados.get("uf"));
            endereco.setDsComplemento(dados.get("complemento"));

            if (dados.get("numero") != null && !dados.get("numero").isEmpty()) {
                endereco.setDsNumero(Integer.parseInt(dados.get("numero")));
            }

            enderecoRepository.save(endereco);
        }
    }
}