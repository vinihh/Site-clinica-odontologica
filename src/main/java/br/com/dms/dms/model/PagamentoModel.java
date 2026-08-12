package br.com.dms.dms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_pagamento")
public class PagamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private PacModel paciente;

    @OneToOne
    @JoinColumn(name = "id_agendamento", nullable = false)
    private AgendamentoModel agendamento;

    @Column(name = "id_forma_pagamento", length = 200)
    private String formaPagamento;

    @Column(name = "nr_cpf_paciente", length = 11)
    private String cpfPaciente;

    @Column(name = "nr_valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "nr_parcelas")
    private Integer parcelas;

    public Long getIdPagamento() { return idPagamento; }
    public void setIdPagamento(Long idPagamento) { this.idPagamento = idPagamento; }

    public PacModel getPaciente() { return paciente; }
    public void setPaciente(PacModel paciente) { this.paciente = paciente; }

    public AgendamentoModel getAgendamento() { return agendamento; }
    public void setAgendamento(AgendamentoModel agendamento) { this.agendamento = agendamento; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public String getCpfPaciente() { return cpfPaciente; }
    public void setCpfPaciente(String cpfPaciente) { this.cpfPaciente = cpfPaciente; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Integer getParcelas() { return parcelas; }
    public void setParcelas(Integer parcelas) { this.parcelas = parcelas; }
}