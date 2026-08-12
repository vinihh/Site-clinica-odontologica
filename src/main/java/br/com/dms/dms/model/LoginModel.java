package br.com.dms.dms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tb_login")
public class LoginModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login")
    private Long idLogin;

    @NotEmpty
    @Column(name = "ds_usuario", length = 200, nullable = false)
    private String dsUsuario;

    @NotEmpty
    @Column(name = "ds_senha", length = 200, nullable = false)
    private String dsSenha;

    @Column(name = "dt_criacao")
    private String dtCriacao;

    @Column(name = "ds_criador")
    private String dsCriador;

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
    }

    public String getDsUsuario() {
        return dsUsuario;
    }

    public void setDsUsuario(String dsUsuario) {
        this.dsUsuario = dsUsuario;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public String getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(String dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public String getDsCriador() {
        return dsCriador;
    }

    public void setDsCriador(String dsCriador) {
        this.dsCriador = dsCriador;
    }
}