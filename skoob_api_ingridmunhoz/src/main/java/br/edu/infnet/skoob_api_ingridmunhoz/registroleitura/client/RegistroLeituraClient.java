package br.edu.infnet.skoob_api_ingridmunhoz.registroleitura.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "registro-leitura-service", url = "${registroleitura.service.url}")
public interface RegistroLeituraClient {

    @GetMapping("/registros-leitura")
    List<RegistroLeituraResponse> obterLista();

    @GetMapping("/registros-leitura/{id}")
    RegistroLeituraResponse obterPorId(@PathVariable Long id);

    @GetMapping("/registros-leitura/usuario/{usuarioId}")
    List<RegistroLeituraResponse> buscarPorUsuario(@PathVariable Long usuarioId);

    @GetMapping("/registros-leitura/livro/{livroId}")
    List<RegistroLeituraResponse> buscarPorLivro(@PathVariable Long livroId);

    @PostMapping("/registros-leitura")
    RegistroLeituraResponse incluir(@RequestBody RegistroLeituraRequest request);

    @PutMapping("/registros-leitura/{id}")
    RegistroLeituraResponse alterar(@PathVariable Long id, @RequestBody RegistroLeituraRequest request);

    @DeleteMapping("/registros-leitura/{id}")
    void excluir(@PathVariable Long id);
}