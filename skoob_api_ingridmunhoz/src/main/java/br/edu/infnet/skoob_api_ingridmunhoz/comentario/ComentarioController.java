package br.edu.infnet.skoob_api_ingridmunhoz.comentario;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    // GET /comentarios - Listar todos os comentários
    @GetMapping
    public ResponseEntity<List<Comentario>> obterLista() {
        List<Comentario> comentarios = comentarioService.obterLista();
        return ResponseEntity.ok(comentarios);
    }

    // GET /comentarios/{id} - Buscar comentário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> obterPorId(@PathVariable Long id) {
        Comentario comentario = comentarioService.obterPorId(id);
        return ResponseEntity.ok(comentario);
    }

    // GET /comentarios/usuario/{usuarioId} - Buscar por usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Comentario>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<Comentario> comentarios = comentarioService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(comentarios);
    }

    // GET /comentarios/livro/{livroId} - Buscar por livro
    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<Comentario>> buscarPorLivro(@PathVariable Long livroId) {
        List<Comentario> comentarios = comentarioService.buscarPorLivro(livroId);
        return ResponseEntity.ok(comentarios);
    }

    // GET /comentarios/avaliacao/{estrelas} - Buscar por avaliação mínima
    @GetMapping("/avaliacao/{estrelas}")
    public ResponseEntity<List<Comentario>> buscarPorAvaliacaoMinima(@PathVariable int estrelas) {
        List<Comentario> comentarios = comentarioService.buscarPorAvaliacaoMinima(estrelas);
        return ResponseEntity.ok(comentarios);
    }

    // GET /comentarios/livro/{livroId}/recentes - Comentários mais recentes de um livro
    @GetMapping("/livro/{livroId}/recentes")
    public ResponseEntity<List<Comentario>> listarRecentesPorLivro(@PathVariable Long livroId) {
        List<Comentario> comentarios = comentarioService.listarPorDataRecente(livroId);
        return ResponseEntity.ok(comentarios);
    }

    // POST /comentarios - Incluir novo comentário
    @PostMapping
    public ResponseEntity<Comentario> incluir(@Valid @RequestBody Comentario comentario) {
        Comentario novoComentario = comentarioService.incluir(comentario);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoComentario.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoComentario);
    }

    // PUT /comentarios/{id} - Alterar comentário
    @PutMapping("/{id}")
    public ResponseEntity<Comentario> alterar(@PathVariable Long id, @Valid @RequestBody Comentario comentario) {
        comentario.setId(id);
        Comentario comentarioAlterado = comentarioService.alterar(comentario);
        return ResponseEntity.ok(comentarioAlterado);
    }

    // DELETE /comentarios/{id} - Excluir comentário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        comentarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}