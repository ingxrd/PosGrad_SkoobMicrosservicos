package br.edu.infnet.skoob_api_ingridmunhoz.livro;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // GET /livros - Listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> obterLista() {
        List<Livro> livros = livroService.obterLista();
        return ResponseEntity.ok(livros);
    }

    // GET /livros/{id} - Buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> obterPorId(@PathVariable Long id) {
        Livro livro = livroService.obterPorId(id);
        return ResponseEntity.ok(livro);
    }

    // GET /livros/buscar?titulo=... - Buscar por título
    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarPorTitulo(@RequestParam(required = false) String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return ResponseEntity.ok(livroService.obterLista());
        }
        List<Livro> livros = livroService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(livros);
    }

    // GET /livros/autor/{autor} - Buscar por autor
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Livro>> buscarPorAutor(@PathVariable String autor) {
        List<Livro> livros = livroService.buscarPorAutor(autor);
        return ResponseEntity.ok(livros);
    }

    // GET /livros/genero/{genero} - Buscar por gênero
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Livro>> buscarPorGenero(@PathVariable String genero) {
        List<Livro> livros = livroService.buscarPorGenero(genero);
        return ResponseEntity.ok(livros);
    }

    // GET /livros/disponiveis - Listar livros disponíveis
    @GetMapping("/disponiveis")
    public ResponseEntity<List<Livro>> listarDisponiveis() {
        List<Livro> livros = livroService.listarDisponiveis();
        return ResponseEntity.ok(livros);
    }

    // GET /livros/top5 - Top 5 melhores avaliados
    @GetMapping("/top5")
    public ResponseEntity<List<Livro>> listarTop5() {
        List<Livro> livros = livroService.listarTop5MelhorAvaliados();
        return ResponseEntity.ok(livros);
    }

    // GET /livros/termo?q=... - Buscar por termo (título ou autor)
    @GetMapping("/termo")
    public ResponseEntity<List<Livro>> buscarPorTermo(@RequestParam String q) {
        List<Livro> livros = livroService.buscarPorTermo(q);
        return ResponseEntity.ok(livros);
    }

    // POST /livros - Incluir novo livro
    @PostMapping
    public ResponseEntity<Livro> incluir(@Valid @RequestBody Livro livro) {
        Livro novoLivro = livroService.incluir(livro);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoLivro.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoLivro);
    }

    // PUT /livros/{id} - Alterar livro
    @PutMapping("/{id}")
    public ResponseEntity<Livro> alterar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        livro.setId(id);
        Livro livroAlterado = livroService.alterar(livro);
        return ResponseEntity.ok(livroAlterado);
    }

    // DELETE /livros/{id} - Excluir livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}