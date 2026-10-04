package br.edu.infnet.skoob_registro_leitura_service.exception;

public class IdentificadorDuplicadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IdentificadorDuplicadoException(String mensagem) {
        super(mensagem);
    }

    public IdentificadorDuplicadoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}