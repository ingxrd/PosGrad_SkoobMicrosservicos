package br.edu.infnet.skoob_api_ingridmunhoz.service;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Comentario;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Usuario;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Livro;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ComentarioService extends BaseService<Comentario> {

    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public ComentarioService(UsuarioService usuarioService, LivroService livroService) {
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    @Override
    protected Long getId(Comentario comentario) {
        return comentario != null ? (long) comentario.getId() : null;
    }

    @Override
    protected void setId(Comentario comentario, Long id) {
        if (comentario != null) {
            comentario.setId(id.intValue());
        }
    }

    // ==================== SOBRESCRITA PARA VALIDAÇÃO ====================

    @Override
    public Comentario incluir(Comentario comentario) {
        validarUsuarioELivro(comentario);
        return super.incluir(comentario);
    }

    @Override
    public Comentario alterar(Comentario comentario) {
        validarUsuarioELivro(comentario);
        return super.alterar(comentario);
    }

    private void validarUsuarioELivro(Comentario comentario) {
        if (comentario == null) {
            return;
        }

        // Verifica se o usuário existe
        if (comentario.getUsuario() != null) {
            Long usuarioId = (long) comentario.getUsuario().getId();
            Usuario usuario = usuarioService.obterPorId(usuarioId);
            if (usuario == null) {
                throw new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado!");
            }
            comentario.setUsuario(usuario);
        }

        // Verifica se o livro existe
        if (comentario.getLivro() != null) {
            Long livroId = (long) comentario.getLivro().getId();
            Livro livro = livroService.obterPorId(livroId);
            if (livro == null) {
                throw new IllegalArgumentException("Livro com ID " + livroId + " não encontrado!");
            }
            comentario.setLivro(livro);
        }
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    /**
     * Busca comentários por usuário.
     */
    public List<Comentario> buscarPorUsuario(Long usuarioId) {
        usuarioService.verificarExistencia(usuarioId);

        return obterLista().stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getId() == usuarioId.intValue())
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Busca comentários por livro.
     */
    public List<Comentario> buscarPorLivro(Long livroId) {
        livroService.verificarExistencia(livroId);

        return obterLista().stream()
                .filter(c -> c.getLivro() != null && c.getLivro().getId() == livroId.intValue())
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Busca comentários com avaliação maior ou igual a um valor.
     * Exemplo de filtro com Stream.
     */
    public List<Comentario> buscarPorAvaliacaoMinima(int estrelas) {
        return obterLista().stream()
                .filter(c -> c.getAvaliacao() >= estrelas)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Lista comentários de um livro ordenados por data (mais recentes primeiro).
     * Exemplo de ordenação com Comparator.
     */
    public List<Comentario> listarPorDataRecente(Long livroId) {
        return buscarPorLivro(livroId).stream()
                .sorted(Comparator.comparing(Comentario::getDataCriacao).reversed())
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Calcula a média de avaliações de comentários.
     * Exemplo de Stream com mapToInt.
     */
    public double calcularMediaAvaliacoesComentarios() {
        return obterLista().stream()
                .mapToInt(Comentario::getAvaliacao)
                .average()
                .orElse(0.0);
    }
}