package br.edu.infnet.skoob_api_ingridmunhoz.exception;

public class IdentificadorDuplicadoException extends RuntimeException {

    public IdentificadorDuplicadoException(String mensagem) {
        super(mensagem);
    }

    public IdentificadorDuplicadoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}