package br.edu.infnet.skoob_api_ingridmunhoz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception.RegistroLeituraNaoEncontradoException;
import br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception.RegistroLeituraServiceIndisponivelException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception.RegistroLeituraNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRegistroLeituraNaoEncontrado(
            br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception.RegistroLeituraNaoEncontradoException ex) {
        return criarResposta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception.RegistroLeituraServiceIndisponivelException.class)
    public ResponseEntity<ErroResponse> tratarRegistroLeituraServiceIndisponivel(
            br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.exception.RegistroLeituraServiceIndisponivelException ex) {
        return criarResposta(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }

    // Trata Recurso Não Encontrado → 404 Not Found
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return criarResposta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Trata Identificador Duplicado → 400 Bad Request (ou 409 Conflict)
    @ExceptionHandler(IdentificadorDuplicadoException.class)
    public ResponseEntity<ErroResponse> tratarIdentificadorDuplicado(IdentificadorDuplicadoException exception){
        return criarResposta(HttpStatus.CONFLICT, exception.getMessage());
    }

    // Trata Argumentos Inválidos → 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return criarResposta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Trata erros de validação dos campos → 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return criarResposta(HttpStatus.BAD_REQUEST, mensagem);
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return criarResposta(HttpStatus.BAD_REQUEST, "JSON inválido ou mal formatado: " + ex.getMostSpecificCause().getMessage());
    }

}
