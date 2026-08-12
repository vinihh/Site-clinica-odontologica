package br.com.dms.dms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;


@Entity
@Table(name = "tb_endereco_paciente")
public class EndPacModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long idEndereco;

    @NotEmpty
    @Column(name = "nm_rua", length = 220, nullable = false)
    private String nmRua;

    @NotEmpty
    @Column(name = "ds_bairro", length = 220, nullable = false)
    private String dsBairro;

    @NotEmpty
    @Column(name = "ds_cidade", length = 30, nullable = false)
    private String dsCidade;

    @NotEmpty
    @Column(name = "ds_uf", length = 5, nullable = false)
    private String dsUf;

    @Column(name = "ds_numero", nullable = false)
    private Integer dsNumero;

    @Column(name = "ds_complemento", length = 220)
    private String dsComplemento;

    @NotEmpty
    @Column(name = "ds_cep", length = 10, nullable = false)
    private String dsCep;


    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private PacModel paciente;


    public PacModel getIdPaciente() {
        return paciente;
    }

    public void setIdPaciente(PacModel idPaciente) {
        this.paciente = idPaciente;
    }


    public Long getIdEndereco() { return idEndereco; }
    public void setIdEndereco(Long idEndereco) { this.idEndereco = idEndereco; }

    public String getNmRua() { return nmRua; }
    public void setNmRua(String nmRua) { this.nmRua = nmRua; }

    public String getDsBairro() { return dsBairro; }
    public void setDsBairro(String dsBairro) { this.dsBairro = dsBairro; }

    public String getDsCidade() { return dsCidade; }
    public void setDsCidade(String dsCidade) { this.dsCidade = dsCidade; }

    public String getDsUf() { return dsUf; }
    public void setDsUf(String dsUf) { this.dsUf = dsUf; }

    public Integer getDsNumero() { return dsNumero; }
    public void setDsNumero(Integer dsNumero) { this.dsNumero = dsNumero; }

    public String getDsComplemento() { return dsComplemento; }
    public void setDsComplemento(String dsComplemento) { this.dsComplemento = dsComplemento; }

    public String getDsCep() { return dsCep; }
    public void setDsCep(String dsCep) { this.dsCep = dsCep; }

}