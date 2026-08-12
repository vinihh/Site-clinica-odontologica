package br.com.dms.dms.controller;

import br.com.dms.dms.model.AgendamentoModel;
import br.com.dms.dms.model.CadEspecModel;
import br.com.dms.dms.model.EspecialidadesModel;
import br.com.dms.dms.model.PacModel;
import br.com.dms.dms.repository.AgendamentoRepository;
import br.com.dms.dms.repository.EspecialidadesRepository;
import br.com.dms.dms.repository.PacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CadAgenController {

    @Autowired
    private PacRepository pacRepository;

    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/cad-agendamentos")
    public String telaCadAgendamentos(Model model) {

        model.addAttribute("pacientes", pacRepository.findAll());

        model.addAttribute("listaEspecialidades", List.of(
                "Limpeza dental", "Clareamento dental", "Aparelho dentário", "Canal",
                "Implante dentário", "Prótese dentária", "Extração de dente", "Dente do siso"
        ));
        return "cad-agendamentos";
    }

    @GetMapping("/api/buscar-doutores")
    @ResponseBody
    public List<CadEspecModel> buscarDoutores(@RequestParam String especialidade) {
        List<EspecialidadesModel> relacoes = especialidadesRepository.findByDsEspecialidade(especialidade);
        List<CadEspecModel> doutores = new ArrayList<>();
        for (EspecialidadesModel esp : relacoes) {
            doutores.add(esp.getFuncionario());
        }
        return doutores;
    }

    @GetMapping("/api/horarios-ocupados")
    @ResponseBody
    public List<String> buscarHorariosOcupados(@RequestParam Long idDoutor, @RequestParam String data) {
        LocalDate dataBusca = LocalDate.parse(data, DateTimeFormatter.ISO_DATE);

        List<AgendamentoModel> agendamentos = agendamentoRepository.findByFuncionarioIdFuncionarioAndDtConsulta(idDoutor, dataBusca);

        List<String> horariosOcupados = new ArrayList<>();

        for (AgendamentoModel ag : agendamentos) {
            if (!"CANCELADO".equalsIgnoreCase(ag.getDsStatusAgendamento())) {
                horariosOcupados.add(ag.getHrConsulta().toString());
            }
        }

        return horariosOcupados;
    }

    @PostMapping("/api/salvar-agendamento")
    @ResponseBody
    public String salvarAgendamento(@RequestBody Map<String, String> dados, jakarta.servlet.http.HttpSession session) {
        try {
            Long idPaciente = Long.parseLong(dados.get("idPaciente"));
            PacModel paciente = pacRepository.findById(idPaciente).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

            CadEspecModel doutor = new CadEspecModel();
            doutor.setIdFuncionario(Long.parseLong(dados.get("idDoutor")));

            AgendamentoModel novoAgendamento = new AgendamentoModel();
            novoAgendamento.setPaciente(paciente);
            novoAgendamento.setFuncionario(doutor);
            novoAgendamento.setDsTipoServico(dados.get("especialidade"));
            novoAgendamento.setDtConsulta(LocalDate.parse(dados.get("data")));
            novoAgendamento.setHrConsulta(LocalTime.parse(dados.get("horario")));
            novoAgendamento.setDsObservacao(dados.get("observacao"));
            novoAgendamento.setVlPreco(new BigDecimal(dados.get("valor")));
            novoAgendamento.setDsStatusAgendamento("AGENDADO");

            String usuarioLogado = (String) session.getAttribute("usuarioLogado");
            novoAgendamento.setUsuarioAtualizacao(usuarioLogado != null ? usuarioLogado : "Sistema");
            novoAgendamento.setDataHoraAtualizacao(java.time.LocalDateTime.now());

            agendamentoRepository.save(novoAgendamento);
            return "Sucesso";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar agendamento");
        }
    }
}