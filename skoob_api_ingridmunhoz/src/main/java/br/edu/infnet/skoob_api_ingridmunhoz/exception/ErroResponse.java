package br.edu.infnet.skoob_api_ingridmunhoz.exception;

import java.time.LocalDateTime;

public class ErroResponse {

    private int status;
    private String erro;
    private String mensagem;
    private LocalDateTime timestamp;

    public ErroResponse() {
    }

    public ErroResponse(int status, String erro, String mensagem, LocalDateTime timestamp) {
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}