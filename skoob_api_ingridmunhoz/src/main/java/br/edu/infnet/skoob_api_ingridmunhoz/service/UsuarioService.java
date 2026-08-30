package br.edu.infnet.skoob_api_ingridmunhoz.service;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Usuario;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

// Classe irá implementar suas especificidades a partir da BaseService, recebendo o dominio Usuario.

@Service
public class UsuarioService extends BaseService<Usuario> {

    @Override
    protected Long getId(Usuario usuario) {
        return usuario != null ? (long) usuario.getId() : null;
    }

    @Override
    protected void setId(Usuario usuario, Long id) {
        if (usuario != null) {
            usuario.setId(id.intValue());
        }
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    /**
     * Busca usuários por nome (case insensitive).
     * Exemplo de filtro com Stream e Lambda.
     */
    public List<Usuario> buscarPorNome(String nome) {
        return filtrarPorTexto(nome, Usuario::getNome);
    }

    /**
     * Busca usuários por username (case insensitive).
     */
    public List<Usuario> buscarPorUsername(String username) {
        return filtrarPorTexto(username, Usuario::getUsername);
    }

    /**
     * Busca um usuário pelo email (exato).
     */
    public Usuario buscarPorEmail(String email) {
        if (email == null) {
            return null;
        }

        return obterLista().stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Lista usuários ordenados por nome (alfabético).
     * Exemplo de ordenação com Comparator.
     */
    public List<Usuario> listarOrdenadosPorNome() {
        return ordenarPor(Comparator.comparing(Usuario::getNome));
    }

    /**
     * Lista usuários ativos (que têm pelo menos um registro de leitura).
     * Exemplo de filtro complexo.
     */
    public List<Usuario> listarUsuariosAtivos() {
        return obterLista().stream()
                .filter(u -> u.getRegistrosLeitura() != null && !u.getRegistrosLeitura().isEmpty())
                .collect(java.util.stream.Collectors.toList());
    }
}