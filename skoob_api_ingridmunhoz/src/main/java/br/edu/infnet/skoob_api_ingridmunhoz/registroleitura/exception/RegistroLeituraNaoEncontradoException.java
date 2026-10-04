package br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception;

public class RegistroLeituraNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RegistroLeituraNaoEncontradoException(Long id) {
        super("Registro de leitura não encontrado. ID: " + id);
    }
}