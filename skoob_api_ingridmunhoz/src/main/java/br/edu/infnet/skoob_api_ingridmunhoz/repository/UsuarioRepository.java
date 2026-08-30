package br.edu.infnet.skoob_api_ingridmunhoz.repository;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    List<Usuario> findByUsernameContainingIgnoreCase(String username);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}