package br.edu.infnet.skoob_api_ingridmunhoz.exception;

public class IdentificadorDuplicadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IdentificadorDuplicadoException(String mensagem) {
        super(mensagem);
    }

    public IdentificadorDuplicadoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}