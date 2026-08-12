/*
package br.com.dms.dms.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AgendamentoModel {

    private String cpf;
    private String nome;
    private String especialidade;
    private String especialista;
    private LocalDate data;
    private String horario;
    private String observacao;
    private BigDecimal valor;
}*/

package br.com.dms.dms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_agendamento")
public class AgendamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Long idAgendamento;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private PacModel paciente;

    @Column(name = "ds_tipo_servico", length = 220)
    private String dsTipoServico;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private CadEspecModel funcionario;

    @Column(name = "dt_consulta")
    private LocalDate dtConsulta;

    @Column(name = "hr_consulta")
    private LocalTime hrConsulta;

    @Column(name = "ds_status_agendamento", length = 40)
    private String dsStatusAgendamento;

    @Column(name = "ds_observacao", length = 220)
    private String dsObservacao;

    @Column(name = "vl_preco", precision = 10, scale = 2)
    private BigDecimal vlPreco;

    public Long getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(Long idAgendamento) { this.idAgendamento = idAgendamento; }

    public PacModel getPaciente() { return paciente; }
    public void setPaciente(PacModel paciente) { this.paciente = paciente; }

    public String getDsTipoServico() { return dsTipoServico; }
    public void setDsTipoServico(String dsTipoServico) { this.dsTipoServico = dsTipoServico; }

    public CadEspecModel getFuncionario() { return funcionario; }
    public void setFuncionario(CadEspecModel funcionario) { this.funcionario = funcionario; }

    public LocalDate getDtConsulta() { return dtConsulta; }
    public void setDtConsulta(LocalDate dtConsulta) { this.dtConsulta = dtConsulta; }

    public LocalTime getHrConsulta() { return hrConsulta; }
    public void setHrConsulta(LocalTime hrConsulta) { this.hrConsulta = hrConsulta; }

    public String getDsStatusAgendamento() { return dsStatusAgendamento; }
    public void setDsStatusAgendamento(String dsStatusAgendamento) { this.dsStatusAgendamento = dsStatusAgendamento; }

    public String getDsObservacao() { return dsObservacao; }
    public void setDsObservacao(String dsObservacao) { this.dsObservacao = dsObservacao; }

    public BigDecimal getVlPreco() { return vlPreco; }
    public void setVlPreco(BigDecimal vlPreco) { this.vlPreco = vlPreco; }

    @OneToOne(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private PagamentoModel pagamento;

    public PagamentoModel getPagamento() { return pagamento; }
    public void setPagamento(PagamentoModel pagamento) { this.pagamento = pagamento; }

    @Column(name = "ds_motivo_cancelamento", length = 255)
    private String dsMotivoCancelamento;

    public String getDsMotivoCancelamento() { return dsMotivoCancelamento; }
    public void setDsMotivoCancelamento(String dsMotivoCancelamento) { this.dsMotivoCancelamento = dsMotivoCancelamento; }

    @Column(name = "nm_usuario_atualizacao", length = 100)
    private String usuarioAtualizacao;

    @Column(name = "dt_atualizacao")
    private java.time.LocalDateTime dataHoraAtualizacao;

    public String getUsuarioAtualizacao() { return usuarioAtualizacao; }
    public void setUsuarioAtualizacao(String usuarioAtualizacao) { this.usuarioAtualizacao = usuarioAtualizacao; }

    public java.time.LocalDateTime getDataHoraAtualizacao() { return dataHoraAtualizacao; }
    public void setDataHoraAtualizacao(java.time.LocalDateTime dataHoraAtualizacao) { this.dataHoraAtualizacao = dataHoraAtualizacao; }

}
