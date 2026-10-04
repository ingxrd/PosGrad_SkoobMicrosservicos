package br.edu.infnet.skoob_api_ingridmunhoz.usuario;

import br.edu.infnet.skoob_api_ingridmunhoz.exception.IdentificadorDuplicadoException;
import br.edu.infnet.skoob_api_ingridmunhoz.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // CRUD
    public Usuario incluir(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    public Usuario alterar(Usuario usuario) {
        Usuario existente = obterPorId(usuario.getId());

        if (!existente.getUsername().equals(usuario.getUsername())
                && usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IdentificadorDuplicadoException(
                    "Username '" + usuario.getUsername() + "' já está em uso!");
        }

        if (!existente.getEmail().equals(usuario.getEmail())
                && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IdentificadorDuplicadoException(
                    "Email '" + usuario.getEmail() + "' já está em uso!");
        }

        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        verificarExistencia(id);
        usuarioRepository.deleteById(id);
    }

    public Usuario obterPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhum usuário encontrado para o identificador " + id));
    }

    public List<Usuario> obterLista() {
        return usuarioRepository.findAll();
    }

    // Validações
    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo!");
        }

        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IdentificadorDuplicadoException(
                    "Username '" + usuario.getUsername() + "' já está em uso!");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IdentificadorDuplicadoException(
                    "Email '" + usuario.getEmail() + "' já está em uso!");
        }
    }

    private void verificarExistencia(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Identificador não pode ser nulo!");
        }
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Nenhum usuário encontrado para o identificador " + id);
        }
    }

    // Consultas com Spring Data
    public List<Usuario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return obterLista();
        }
        return usuarioRepository.findByNomeContainingIgnoreCase(nome.trim());
    }

    public List<Usuario> buscarPorUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return obterLista();
        }
        return usuarioRepository.findByUsernameContainingIgnoreCase(username.trim());
    }

    public Usuario buscarPorEmail(String email) {
        if (email == null) {
            return null;
        }
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public Usuario buscarPorUsernameExato(String username) {
        if (username == null) {
            return null;
        }
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    public boolean existePorUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
}