package br.edu.infnet.skoob_api_ingridmunhoz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata Recurso Não Encontrado → 404 Not Found
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return criarResposta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Trata Identificador Duplicado → 400 Bad Request (ou 409 Conflict)
    @ExceptionHandler(IdentificadorDuplicadoException.class)
    public ResponseEntity<ErroResponse> handleIdentificadorDuplicado(IdentificadorDuplicadoException ex) {
        return criarResposta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Trata Argumentos Inválidos → 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return criarResposta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Método privado para criar resposta padronizada
    private ResponseEntity<ErroResponse> criarResposta(HttpStatus status, String mensagem) {
        ErroResponse erro = new ErroResponse(
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erro);
    }
}