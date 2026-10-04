package br.edu.infnet.skoob_api_ingridmunhoz.comentario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByUsuarioId(Long usuarioId);

    List<Comentario> findByLivroId(Long livroId);

    List<Comentario> findByAvaliacaoGreaterThanEqual(int avaliacao);

    List<Comentario> findByLivroIdOrderByDataCriacaoDesc(Long livroId);

    List<Comentario> findByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId);
}