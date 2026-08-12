package br.com.dms.dms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tb_paciente_telefone")
public class TelPacModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefone")
    private Long idTelefone;

    @NotEmpty
    @Column(name = "ds_telefone", length = 15, nullable = false)
    private String dsTelefone;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private PacModel idPaciente;

    public PacModel getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(PacModel idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdTelefone() { return idTelefone; }
    public void setIdTelefone(Long idTelefone) { this.idTelefone = idTelefone; }

    public String getDsTelefone() { return dsTelefone; }
    public void setDsTelefone(String dsTelefone) { this.dsTelefone = dsTelefone; }

}