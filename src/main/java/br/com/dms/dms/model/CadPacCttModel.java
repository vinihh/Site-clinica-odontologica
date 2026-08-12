    package br.com.dms.dms.model;

    import jakarta.persistence.Column;
    import jakarta.validation.constraints.NotEmpty;
    import java.io.Serializable;

    public class CadPacCttModel implements Serializable {

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

        @NotEmpty
        @Column(name = "ds_telefone", length = 15, nullable = false)
        private String dsTelefone;

        @NotEmpty
        @Column(name = "ds_email", length = 220, nullable = false)
        private String dsEmail;

        @Column(name = "id_paciente")
        private Long idPaciente;

        public Long getIdPaciente() {
            return idPaciente;
        }

        public void setIdPaciente(Long idPaciente) {
            this.idPaciente = idPaciente;
        }

        public String getNmRua() {
            return nmRua;
        }

        public void setNmRua(String nmRua) {
            this.nmRua = nmRua;
        }

        public String getDsBairro() {
            return dsBairro;
        }

        public void setDsBairro(String dsBairro) {
            this.dsBairro = dsBairro;
        }

        public String getDsCidade() {
            return dsCidade;
        }

        public void setDsCidade(String dsCidade) {
            this.dsCidade = dsCidade;
        }

        public String getDsUf() {
            return dsUf;
        }

        public void setDsUf(String dsUf) {
            this.dsUf = dsUf;
        }

        public Integer getDsNumero() {
            return dsNumero;
        }

        public void setDsNumero(Integer dsNumero) {
            this.dsNumero = dsNumero;
        }

        public String getDsComplemento() {
            return dsComplemento;
        }

        public void setDsComplemento(String dsComplemento) {
            this.dsComplemento = dsComplemento;
        }

        public String getDsCep() {
            return dsCep;
        }

        public void setDsCep(String dsCep) {
            this.dsCep = dsCep;
        }

        public String getDsTelefone() {
            return dsTelefone;
        }

        public void setDsTelefone(String dsTelefone) {
            this.dsTelefone = dsTelefone;
        }

        public String getDsEmail() {
            return dsEmail;
        }

        public void setDsEmail(String dsEmail) {
            this.dsEmail = dsEmail;
        }
    }