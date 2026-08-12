package br.com.dms.dms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_paciente")
public class PacModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long idPaciente;

    @NotEmpty
    @Column(name = "nm_paciente", length = 220, nullable = false)
    private String nmPaciente;

    @NotEmpty
    @Column(name = "ds_cpf", length = 20, nullable = false)
    private String dsCpf;

    @NotEmpty
    @Column(name = "ds_rg", length = 20, nullable = false)
    private String dsRg;

    @NotEmpty
    @Column(name = "dt_nascimento", nullable = false)
    private String dtNascimento;

    @OneToMany(mappedBy = "paciente")
    private List<EndPacModel> enderecos;

    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }

    public String getNmPaciente() { return nmPaciente; }
    public void setNmPaciente(String nmPaciente) { this.nmPaciente = nmPaciente; }

    public String getDsCpf() { return dsCpf; }
    public void setDsCpf(String dsCpf) { this.dsCpf = dsCpf; }

    public String getDsRg() { return dsRg; }
    public void setDsRg(String dsRg) { this.dsRg = dsRg; }

    public String getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(String dtNascimento) { this.dtNascimento = dtNascimento; }

    @OneToMany(mappedBy = "idPaciente", cascade = CascadeType.ALL)
    private List<EmailPacModel> emails;

    @OneToMany(mappedBy = "idPaciente", cascade = CascadeType.ALL)
    private List<TelPacModel> telefones;

    public List<EmailPacModel> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailPacModel> emails) {
        this.emails = emails;
    }

    public List<TelPacModel> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelPacModel> telefones) {
        this.telefones = telefones;
    }

    public List<EndPacModel> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EndPacModel> enderecos) {
        this.enderecos = enderecos;
    }

    @Column(name = "nm_usuario_atualizacao", length = 100)
    private String usuarioAtualizacao;

    @Column(name = "dt_atualizacao")
    private java.time.LocalDateTime dataHoraAtualizacao;

    public String getUsuarioAtualizacao() { return usuarioAtualizacao; }
    public void setUsuarioAtualizacao(String usuarioAtualizacao) { this.usuarioAtualizacao = usuarioAtualizacao; }

    public java.time.LocalDateTime getDataHoraAtualizacao() { return dataHoraAtualizacao; }
    public void setDataHoraAtualizacao(java.time.LocalDateTime dataHoraAtualizacao) { this.dataHoraAtualizacao = dataHoraAtualizacao; }
}

