package br.edu.infnet.skoob_api_ingridmunhoz.service;

import br.edu.infnet.skoob_api_ingridmunhoz.exception.RecursoNaoEncontradoException;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.RegistroLeitura;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Usuario;
import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.repository.RegistroLeituraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroLeituraService {

    private final RegistroLeituraRepository registroRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public RegistroLeituraService(RegistroLeituraRepository registroRepository,
                                  UsuarioService usuarioService,
                                  LivroService livroService) {
        this.registroRepository = registroRepository;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    // CRUD
    public RegistroLeitura incluir(RegistroLeitura registro) {
        validarRegistro(registro);
        return registroRepository.save(registro);
    }

    public RegistroLeitura alterar(RegistroLeitura registro) {
        verificarExistencia(registro.getId());
        validarRegistro(registro);
        return registroRepository.save(registro);
    }

    public void excluir(Long id) {
        verificarExistencia(id);
        registroRepository.deleteById(id);
    }

    public RegistroLeitura obterPorId(Long id) {
        return registroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhum registro de leitura encontrado para o identificador " + id));
    }

    public List<RegistroLeitura> obterLista() {
        return registroRepository.findAll();
    }

    // Validações
    private void validarRegistro(RegistroLeitura registro) {
        if (registro == null) {
            throw new IllegalArgumentException("Registro não pode ser nulo!");
        }

        // Verifica se o usuário existe
        if (registro.getUsuario() != null && registro.getUsuario().getId() != null) {
            Long usuarioId = registro.getUsuario().getId();
            Usuario usuario = usuarioService.obterPorId(usuarioId);
            registro.setUsuario(usuario);
        }

        // Verifica se o livro existe
        if (registro.getLivro() != null && registro.getLivro().getId() != null) {
            Long livroId = registro.getLivro().getId();
            Livro livro = livroService.obterPorId(livroId);
            registro.setLivro(livro);
        }
    }

    private void verificarExistencia(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Identificador não pode ser nulo!");
        }
        if (!registroRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Nenhum registro de leitura encontrado para o identificador " + id);
        }
    }

    // Consultas
    public List<RegistroLeitura> buscarPorUsuario(Long usuarioId) {
        usuarioService.obterPorId(usuarioId);
        return registroRepository.findByUsuarioId(usuarioId);
    }

    public List<RegistroLeitura> buscarPorLivro(Long livroId) {
        livroService.obterPorId(livroId);
        return registroRepository.findByLivroId(livroId);
    }

    public List<RegistroLeitura> buscarPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return obterLista();
        }
        return registroRepository.findByStatus(status);
    }

    public List<RegistroLeitura> listarEmAndamento() {
        return registroRepository.findByPercentualLeituraLessThan(100.0);
    }

    public List<RegistroLeitura> listarFinalizados() {
        return registroRepository.findByStatus("TERMINADO");
    }

    public List<RegistroLeitura> listarNaoIniciados() {
        return registroRepository.findByStatusAndPercentualLeituraEquals("NAO_INICIADO", 0.0);
    }

    public double calcularMediaAvaliacoes() {
        return registroRepository.findAll().stream()
                .mapToInt(RegistroLeitura::getAvaliacaoUsuario)
                .average()
                .orElse(0.0);
    }
}