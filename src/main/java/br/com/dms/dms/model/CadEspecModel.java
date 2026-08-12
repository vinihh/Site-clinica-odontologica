package br.com.dms.dms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tb_funcionario")
public class CadEspecModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @NotEmpty
    @Column(name = "nm_funcionario", nullable = false)
    private String nmFuncionario;

    @NotEmpty
    @Column(name = "ds_cpf", nullable = false)
    private String dsCpf;

    @NotEmpty
    @Column(name = "ds_email", nullable = false)
    private String dsEmail;

    @NotEmpty
    @Column(name = "ds_telefone", nullable = false)
    private String dsTelefone;

    @NotEmpty
    @Column(name = "ds_cro", nullable = false)
    private String dsCro;

    @NotEmpty
    @Column(name = "dt_nascimento", nullable = false)
    private String dtNascimento;


    public Long getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(Long idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getNmFuncionario() { return nmFuncionario; }
    public void setNmFuncionario(String nmFuncionario) { this.nmFuncionario = nmFuncionario; }

    public String getDsCpf() { return dsCpf; }
    public void setDsCpf(String dsCpf) { this.dsCpf = dsCpf; }

    public String getDsEmail() { return dsEmail; }
    public void setDsEmail(String dsEmail) { this.dsEmail = dsEmail; }

    public String getDsTelefone() { return dsTelefone; }
    public void setDsTelefone(String dsTelefone) { this.dsTelefone = dsTelefone; }

    public String getDsCro() { return dsCro; }
    public void setDsCro(String dsCro) { this.dsCro = dsCro; }

    public String getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(String dtNascimento) { this.dtNascimento = dtNascimento; }

    @Transient
    private String especialidadesTexto;

    public String getEspecialidadesTexto() {
        return especialidadesTexto;
    }

    public void setEspecialidadesTexto(String especialidadesTexto) {
        this.especialidadesTexto = especialidadesTexto;
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