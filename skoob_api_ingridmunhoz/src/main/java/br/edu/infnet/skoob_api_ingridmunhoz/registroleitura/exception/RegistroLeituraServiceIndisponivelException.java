package br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception;

public class RegistroLeituraServiceIndisponivelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RegistroLeituraServiceIndisponivelException() {
        super("Não foi possível acessar o serviço de registro de leitura.");
    }
}