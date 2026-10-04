package br.edu.infnet.skoob_api_ingridmunhoz.comentario;

import br.edu.infnet.skoob_api_ingridmunhoz.comentario.dto.NotificacaoComentarioDTO;
import br.edu.infnet.skoob_api_ingridmunhoz.exception.RecursoNaoEncontradoException;
import br.edu.infnet.skoob_api_ingridmunhoz.livro.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.livro.LivroService;
import br.edu.infnet.skoob_api_ingridmunhoz.usuario.Usuario;
import br.edu.infnet.skoob_api_ingridmunhoz.usuario.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final NotificacaoComentarioProducer producer;

    public ComentarioService(ComentarioRepository comentarioRepository,
                             UsuarioService usuarioService,
                             LivroService livroService,
                             NotificacaoComentarioProducer producer) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
        this.producer = producer;
    }

    // CRUD
    public Comentario incluir(Comentario comentario) {
        validarComentario(comentario);
        Comentario salvo = comentarioRepository.save(comentario);
        publicarNotificacao(salvo);
        return salvo;
    }

    public Comentario alterar(Comentario comentario) {
        verificarExistencia(comentario.getId());
        validarComentario(comentario);
        return comentarioRepository.save(comentario);
    }

    public void excluir(Long id) {
        verificarExistencia(id);
        comentarioRepository.deleteById(id);
    }

    public Comentario obterPorId(Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhum comentário encontrado para o identificador " + id));
    }

    public List<Comentario> obterLista() {
        return comentarioRepository.findAll();
    }

    // Validações
    private void validarComentario(Comentario comentario) {
        if (comentario == null) {
            throw new IllegalArgumentException("Comentário não pode ser nulo!");
        }

        if (comentario.getUsuario() != null && comentario.getUsuario().getId() != null) {
            Usuario usuario = usuarioService.obterPorId(comentario.getUsuario().getId());
            comentario.setUsuario(usuario);
        }

        if (comentario.getLivro() != null && comentario.getLivro().getId() != null) {
            Livro livro = livroService.obterPorId(comentario.getLivro().getId());
            comentario.setLivro(livro);
        }
    }

    private void verificarExistencia(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Identificador não pode ser nulo!");
        }
        if (!comentarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Nenhum comentário encontrado para o identificador " + id);
        }
    }

    // Consultas
    public List<Comentario> buscarPorUsuario(Long usuarioId) {
        usuarioService.obterPorId(usuarioId);
        return comentarioRepository.findByUsuarioId(usuarioId);
    }

    public List<Comentario> buscarPorLivro(Long livroId) {
        livroService.obterPorId(livroId);
        return comentarioRepository.findByLivroId(livroId);
    }

    public List<Comentario> buscarPorAvaliacaoMinima(int estrelas) {
        return comentarioRepository.findByAvaliacaoGreaterThanEqual(estrelas);
    }

    public List<Comentario> listarPorDataRecente(Long livroId) {
        return comentarioRepository.findByLivroIdOrderByDataCriacaoDesc(livroId);
    }

    public List<Comentario> listarRecentesPorUsuario(Long usuarioId) {
        return comentarioRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId);
    }

    public double calcularMediaAvaliacoes() {
        return comentarioRepository.findAll().stream()
                .mapToInt(Comentario::getAvaliacao)
                .average()
                .orElse(0.0);
    }

    // Mensageria — publica notificação depois de salvar
    private void publicarNotificacao(Comentario salvo) {
        NotificacaoComentarioDTO dto = new NotificacaoComentarioDTO(
                salvo.getId(),
                salvo.getLivro().getId(),
                salvo.getLivro().getTitulo(),
                salvo.getUsuario().getId(),
                salvo.getUsuario().getNome()
        );
        producer.publicar(dto);
    }
}