package br.com.dms.dms.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_especialidade")
public class EspecialidadesModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidade")
    private Long idEspecialidade;

    @Column(name = "ds_especialidade", nullable = false)
    private String dsEspecialidade;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private CadEspecModel funcionario;

    public Long getIdEspecialidade() { return idEspecialidade; }
    public void setIdEspecialidade(Long idEspecialidade) { this.idEspecialidade = idEspecialidade; }

    public String getDsEspecialidade() { return dsEspecialidade; }
    public void setDsEspecialidade(String dsEspecialidade) { this.dsEspecialidade = dsEspecialidade; }

    public CadEspecModel getFuncionario() { return funcionario; }
    public void setFuncionario(CadEspecModel funcionario) { this.funcionario = funcionario; }
}