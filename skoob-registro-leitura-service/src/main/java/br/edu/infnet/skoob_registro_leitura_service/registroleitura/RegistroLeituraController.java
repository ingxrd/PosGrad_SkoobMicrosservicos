package br.edu.infnet.skoob_registro_leitura_service.registroleitura;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/registros-leitura")
public class RegistroLeituraController {

    private final RegistroLeituraService registroService;

    public RegistroLeituraController(RegistroLeituraService registroService) {
        this.registroService = registroService;
    }

    @GetMapping
    public ResponseEntity<List<RegistroLeituraResponse>> obterLista() {
        List<RegistroLeituraResponse> lista = registroService.obterLista()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroLeituraResponse> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(registroService.obterPorId(id)));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RegistroLeituraResponse>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<RegistroLeituraResponse> lista = registroService.buscarPorUsuario(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<RegistroLeituraResponse>> buscarPorLivro(@PathVariable Long livroId) {
        List<RegistroLeituraResponse> lista = registroService.buscarPorLivro(livroId)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RegistroLeituraResponse>> buscarPorStatus(@PathVariable String status) {
        List<RegistroLeituraResponse> lista = registroService.buscarPorStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/em-andamento")
    public ResponseEntity<List<RegistroLeituraResponse>> listarEmAndamento() {
        List<RegistroLeituraResponse> lista = registroService.listarEmAndamento()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/finalizados")
    public ResponseEntity<List<RegistroLeituraResponse>> listarFinalizados() {
        List<RegistroLeituraResponse> lista = registroService.listarFinalizados()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<RegistroLeituraResponse> incluir(@Valid @RequestBody RegistroLeituraRequest request) {
        RegistroLeitura novo = registroService.incluir(toEntity(request));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novo.getId())
                .toUri();
        return ResponseEntity.created(location).body(toResponse(novo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroLeituraResponse> alterar(@PathVariable Long id,
                                                           @Valid @RequestBody RegistroLeituraRequest request) {
        RegistroLeitura entity = toEntity(request);
        entity.setId(id);
        RegistroLeitura alterado = registroService.alterar(entity);
        return ResponseEntity.ok(toResponse(alterado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        registroService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // ============ Conversões ============
    private RegistroLeitura toEntity(RegistroLeituraRequest req) {
        return new RegistroLeitura(
                req.status(),
                req.paginasLidas(),
                req.percentualLeitura(),
                req.avaliacaoUsuario(),
                req.dataInicio(),
                req.dataConclusao(),
                req.usuarioId(),
                req.livroId()
        );
    }

    private RegistroLeituraResponse toResponse(RegistroLeitura r) {
        return new RegistroLeituraResponse(
                r.getId(),
                r.getStatus(),
                r.getPaginasLidas(),
                r.getPercentualLeitura(),
                r.getAvaliacaoUsuario(),
                r.getDataInicio(),
                r.getDataConclusao(),
                r.getUsuarioId(),
                r.getLivroId()
        );
    }
}