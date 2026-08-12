package br.com.dms.dms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "tb_paciente_email")
public class EmailPacModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_email")
    private Long idEmail;

    @NotEmpty
    @Column(name = "ds_email", length = 220, nullable = false)
    private String dsEmail;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private PacModel idPaciente;

    public PacModel getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(PacModel idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdEmail() { return idEmail; }
    public void setIdEmail(Long idEmail) { this.idEmail = idEmail; }

    public String getDsEmail() { return dsEmail; }
    public void setDsEmail(String dsEmail) { this.dsEmail = dsEmail; }

}
