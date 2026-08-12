package br.com.dms.dms.controller;

import br.com.dms.dms.model.AgendamentoModel;
import br.com.dms.dms.model.PagamentoModel;
import br.com.dms.dms.repository.AgendamentoRepository;
import br.com.dms.dms.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String dataFiltro,
            @RequestParam(required = false) String statusFiltro,
            @RequestParam(required = false) String ordenacao,
            Model model) {

        List<AgendamentoModel> lista = agendamentoRepository.findAll();

        if (dataFiltro != null && !dataFiltro.isEmpty()) {
            LocalDate data = LocalDate.parse(dataFiltro);
            lista = lista.stream().filter(a -> a.getDtConsulta().equals(data)).collect(Collectors.toList());
        }

        if (statusFiltro != null && !statusFiltro.isEmpty()) {
            lista = lista.stream().filter(a -> a.getDsStatusAgendamento().equalsIgnoreCase(statusFiltro)).collect(Collectors.toList());
        }

        if (ordenacao != null && !ordenacao.isEmpty()) {
            switch (ordenacao) {
                case "hora":
                    lista.sort(Comparator.comparing(AgendamentoModel::getDtConsulta).thenComparing(AgendamentoModel::getHrConsulta));
                    break;
                case "especialidade":
                    lista.sort(Comparator.comparing(AgendamentoModel::getDsTipoServico));
                    break;
                case "especialista":
                    lista.sort(Comparator.comparing(a -> a.getFuncionario().getNmFuncionario()));
                    break;
            }
        } else {
            lista.sort(Comparator.comparing(AgendamentoModel::getDtConsulta).thenComparing(AgendamentoModel::getHrConsulta));
        }

        model.addAttribute("agendamentos", lista);
        return "agendamentos";
    }

    @PostMapping("/api/mudar-status")
    @ResponseBody
    public String mudarStatus(@RequestBody Map<String, String> dados, jakarta.servlet.http.HttpSession session) {
        Long id = Long.parseLong(dados.get("id"));
        String status = dados.get("status");

        AgendamentoModel ag = agendamentoRepository.findById(id).orElseThrow();
        ag.setDsStatusAgendamento(status);

        if ("CANCELADO".equals(status)) {
            ag.setDsMotivoCancelamento(dados.get("motivo"));
        } else if ("REMARCADO".equals(status)) {
            ag.setDtConsulta(LocalDate.parse(dados.get("data")));
            ag.setHrConsulta(LocalTime.parse(dados.get("horario")));
        }

        String usuarioLogado = (String) session.getAttribute("usuarioLogado");
        ag.setUsuarioAtualizacao(usuarioLogado != null ? usuarioLogado : "Sistema");
        ag.setDataHoraAtualizacao(java.time.LocalDateTime.now());

        agendamentoRepository.save(ag);
        return "Sucesso";
    }

    @PostMapping("/api/finalizar-pagamento")
    @ResponseBody
    public String finalizarPagamento(@RequestBody Map<String, String> dados, jakarta.servlet.http.HttpSession session) {
        Long idAgendamento = Long.parseLong(dados.get("idAgendamento"));
        AgendamentoModel ag = agendamentoRepository.findById(idAgendamento).orElseThrow();

        ag.setDsStatusAgendamento("PAGO");

        String usuarioLogado = (String) session.getAttribute("usuarioLogado");
        ag.setUsuarioAtualizacao(usuarioLogado != null ? usuarioLogado : "Sistema");
        ag.setDataHoraAtualizacao(java.time.LocalDateTime.now());

        agendamentoRepository.save(ag);

        PagamentoModel pag = new PagamentoModel();
        pag.setAgendamento(ag);
        pag.setPaciente(ag.getPaciente());
        pag.setCpfPaciente(ag.getPaciente().getDsCpf().replaceAll("\\D", ""));
        pag.setValor(new BigDecimal(dados.get("valor").replace(",", ".")));

        String forma = dados.get("formaPagamento");
        pag.setFormaPagamento(forma);

        if ("Cartão de Crédito".equals(forma) && dados.get("parcelas") != null) {
            pag.setParcelas(Integer.parseInt(dados.get("parcelas")));
        } else {
            pag.setParcelas(1);
        }

        pagamentoRepository.save(pag);
        return "Sucesso";
    }
}