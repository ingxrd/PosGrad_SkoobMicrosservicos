package br.edu.infnet.skoob_api_ingridmunhoz.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}