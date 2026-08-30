package br.edu.infnet.skoob_api_ingridmunhoz.service;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.RegistroLeitura;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Usuario;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Livro;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RegistroLeituraService extends BaseService<RegistroLeitura> {

    private final UsuarioService usuarioService;
    private final LivroService livroService;

    // Injeção de dependência via construtor
    public RegistroLeituraService(UsuarioService usuarioService, LivroService livroService) {
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    @Override
    protected Long getId(RegistroLeitura registro) {
        return registro != null ? (long) registro.getId() : null;
    }

    @Override
    protected void setId(RegistroLeitura registro, Long id) {
        if (registro != null) {
            registro.setId(id.intValue());
        }
    }

    // ==================== SOBRESCRITA PARA VALIDAÇÃO COMPLETA ====================

    /**
     * Sobrescreve o método incluir para validar usuário e livro.
     */
    @Override
    public RegistroLeitura incluir(RegistroLeitura registro) {
        validarUsuarioELivro(registro);
        return super.incluir(registro);
    }

    @Override
    public RegistroLeitura alterar(RegistroLeitura registro) {
        validarUsuarioELivro(registro);
        return super.alterar(registro);
    }

    private void validarUsuarioELivro(RegistroLeitura registro) {
        if (registro == null) {
            return;
        }

        // Verifica se o usuário existe
        if (registro.getUsuario() != null) {
            Long usuarioId = (long) registro.getUsuario().getId();
            Usuario usuario = usuarioService.obterPorId(usuarioId);
            if (usuario == null) {
                throw new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado!");
            }
            registro.setUsuario(usuario);
        }

        // Verifica se o livro existe
        if (registro.getLivro() != null) {
            Long livroId = (long) registro.getLivro().getId();
            Livro livro = livroService.obterPorId(livroId);
            if (livro == null) {
                throw new IllegalArgumentException("Livro com ID " + livroId + " não encontrado!");
            }
            registro.setLivro(livro);
        }
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    /**
     * Busca registros de leitura por usuário.
     */
    public List<RegistroLeitura> buscarPorUsuario(Long usuarioId) {
        usuarioService.verificarExistencia(usuarioId);

        return obterLista().stream()
                .filter(r -> r.getUsuario() != null && r.getUsuario().getId() == usuarioId.intValue())
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Busca registros de leitura por livro.
     */
    public List<RegistroLeitura> buscarPorLivro(Long livroId) {
        livroService.verificarExistencia(livroId);

        return obterLista().stream()
                .filter(r -> r.getLivro() != null && r.getLivro().getId() == livroId.intValue())
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Busca registros por status.
     */
    public List<RegistroLeitura> buscarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return obterLista();
        }

        return obterLista().stream()
                .filter(r -> status.equalsIgnoreCase(r.getStatus()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Lista registros de leitura ordenados por percentual de leitura (do maior para o menor).
     */
    public List<RegistroLeitura> listarPorProgresso() {
        return ordenarPor(Comparator.comparing(RegistroLeitura::getPercentualLeitura).reversed());
    }

    /**
     * Lista registros em andamento (status "LENDO" ou "NAO_INICIADO" com progresso < 100).
     */
    public List<RegistroLeitura> listarEmAndamento() {
        return obterLista().stream()
                .filter(r -> r.getPercentualLeitura() < 100.0)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Lista registros finalizados (status "TERMINADO").
     */
    public List<RegistroLeitura> listarFinalizados() {
        return buscarPorStatus("TERMINADO");
    }

    /**
     * Calcula a média de avaliações dos registros de leitura.
     * Exemplo de Stream com mapToInt e average.
     */
    public double calcularMediaAvaliacoes() {
        return obterLista().stream()
                .mapToInt(RegistroLeitura::getAvaliacaoUsuario)
                .average()
                .orElse(0.0);
    }
}