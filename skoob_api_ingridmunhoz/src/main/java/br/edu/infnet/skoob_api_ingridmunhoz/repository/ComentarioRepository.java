package br.edu.infnet.skoob_api_ingridmunhoz.repository;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByUsuarioId(Long usuarioId);

    List<Comentario> findByLivroId(Long livroId);

    List<Comentario> findByAvaliacaoGreaterThanEqual(int avaliacao);

    List<Comentario> findByLivroIdOrderByDataCriacaoDesc(Long livroId);

    List<Comentario> findByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId);
}