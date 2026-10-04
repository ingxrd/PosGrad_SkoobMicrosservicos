package br.edu.infnet.skoob_api_ingridmunhoz.registroleitura;

import br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.client.RegistroLeituraGateway;
import br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.client.RegistroLeituraRequest;
import br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.client.RegistroLeituraResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros-leitura")
public class RegistroLeituraController {

    private final RegistroLeituraGateway gateway;

    public RegistroLeituraController(RegistroLeituraGateway gateway) {
        this.gateway = gateway;
    }

    @GetMapping
    public ResponseEntity<List<RegistroLeituraResponse>> obterLista() {
        return ResponseEntity.ok(gateway.obterLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroLeituraResponse> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(gateway.obterPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RegistroLeituraResponse>> buscarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(gateway.buscarPorUsuario(usuarioId));
    }

    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<RegistroLeituraResponse>> buscarPorLivro(@PathVariable Long livroId) {
        return ResponseEntity.ok(gateway.buscarPorLivro(livroId));
    }

    @PostMapping
    public ResponseEntity<RegistroLeituraResponse> incluir(@RequestBody RegistroLeituraRequest request) {
        return ResponseEntity.status(201).body(gateway.incluir(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroLeituraResponse> alterar(@PathVariable Long id,
                                                           @RequestBody RegistroLeituraRequest request) {
        return ResponseEntity.ok(gateway.alterar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        gateway.excluir(id);
        return ResponseEntity.noContent().build();
    }
}