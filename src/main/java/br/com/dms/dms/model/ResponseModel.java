package br.com.dms.dms.model;

public class ResponseModel {

    private String status;
    private String mensagem;

    public ResponseModel(String status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    public String getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }
}
