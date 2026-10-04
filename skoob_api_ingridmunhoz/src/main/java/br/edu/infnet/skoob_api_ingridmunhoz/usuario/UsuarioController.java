package br.edu.infnet.skoob_api_ingridmunhoz.usuario;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Injeção de Dependência pelo construtor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /usuarios - Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> obterLista() {
        List<Usuario> usuarios = usuarioService.obterLista();
        return ResponseEntity.ok(usuarios);
    }

    // GET /usuarios/{id} - Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obterPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // GET /usuarios/buscar?nome=... - Buscar por nome (Query Parameter)
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(required = false) String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.ok(usuarioService.obterLista());
        }
        List<Usuario> usuarios = usuarioService.buscarPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }

    // GET /usuarios/username/{username} - Buscar por username (Path Variable)
    @GetMapping("/username/{username}")
    public ResponseEntity<List<Usuario>> buscarPorUsername(@PathVariable String username) {
        List<Usuario> usuarios = usuarioService.buscarPorUsername(username);
        return ResponseEntity.ok(usuarios);
    }

    // POST /usuarios - Incluir novo usuário
    @PostMapping
    public ResponseEntity<Usuario> incluir(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.incluir(usuario);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoUsuario.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoUsuario);
    }

    // PUT /usuarios/{id} - Alterar usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> alterar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioAlterado = usuarioService.alterar(usuario);
        return ResponseEntity.ok(usuarioAlterado);
    }

    // DELETE /usuarios/{id} - Excluir usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}