package br.edu.infnet.skoob_registro_leitura_service.registroleitura;

import br.edu.infnet.skoob_registro_leitura_service.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroLeituraService {

    private final RegistroLeituraRepository registroRepository;

    public RegistroLeituraService(RegistroLeituraRepository registroRepository) {
        this.registroRepository = registroRepository;
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

    private void validarRegistro(RegistroLeitura registro) {
        if (registro == null) {
            throw new IllegalArgumentException("Registro não pode ser nulo!");
        }
        if (registro.getUsuarioId() == null) {
            throw new IllegalArgumentException("O usuário é obrigatório!");
        }
        if (registro.getLivroId() == null) {
            throw new IllegalArgumentException("O livro é obrigatório!");
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
        return registroRepository.findByUsuarioId(usuarioId);
    }

    public List<RegistroLeitura> buscarPorLivro(Long livroId) {
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