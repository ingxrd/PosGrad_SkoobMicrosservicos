package br.edu.infnet.skoob_api_ingridmunhoz.registroLeitura;

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

    // GET /registros-leitura - Listar todos os registros
    @GetMapping
    public ResponseEntity<List<RegistroLeitura>> obterLista() {
        List<RegistroLeitura> registros = registroService.obterLista();
        return ResponseEntity.ok(registros);
    }

    // GET /registros-leitura/{id} - Buscar registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<RegistroLeitura> obterPorId(@PathVariable Long id) {
        RegistroLeitura registro = registroService.obterPorId(id);
        return ResponseEntity.ok(registro);
    }

    // GET /registros-leitura/usuario/{usuarioId} - Buscar por usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RegistroLeitura>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<RegistroLeitura> registros = registroService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(registros);
    }

    // GET /registros-leitura/livro/{livroId} - Buscar por livro
    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<RegistroLeitura>> buscarPorLivro(@PathVariable Long livroId) {
        List<RegistroLeitura> registros = registroService.buscarPorLivro(livroId);
        return ResponseEntity.ok(registros);
    }

    // GET /registros-leitura/status/{status} - Buscar por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RegistroLeitura>> buscarPorStatus(@PathVariable String status) {
        List<RegistroLeitura> registros = registroService.buscarPorStatus(status);
        return ResponseEntity.ok(registros);
    }

    // GET /registros-leitura/em-andamento - Registros em andamento
    @GetMapping("/em-andamento")
    public ResponseEntity<List<RegistroLeitura>> listarEmAndamento() {
        List<RegistroLeitura> registros = registroService.listarEmAndamento();
        return ResponseEntity.ok(registros);
    }

    // GET /registros-leitura/finalizados - Registros finalizados
    @GetMapping("/finalizados")
    public ResponseEntity<List<RegistroLeitura>> listarFinalizados() {
        List<RegistroLeitura> registros = registroService.listarFinalizados();
        return ResponseEntity.ok(registros);
    }

    // POST /registros-leitura - Incluir novo registro
    @PostMapping
    public ResponseEntity<RegistroLeitura> incluir(@Valid @RequestBody RegistroLeitura registro) {
        RegistroLeitura novoRegistro = registroService.incluir(registro);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoRegistro.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoRegistro);
    }

    // PUT /registros-leitura/{id} - Alterar registro
    @PutMapping("/{id}")
    public ResponseEntity<RegistroLeitura> alterar(@PathVariable Long id, @Valid @RequestBody RegistroLeitura registro) {
        registro.setId(id);
        RegistroLeitura registroAlterado = registroService.alterar(registro);
        return ResponseEntity.ok(registroAlterado);
    }

    // DELETE /registros-leitura/{id} - Excluir registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        registroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}