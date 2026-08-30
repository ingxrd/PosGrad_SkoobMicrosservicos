package br.edu.infnet.skoob_api_ingridmunhoz.repository;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    boolean existsByIsbn(String isbn);
    // Consultas Derivadas
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByAutorContainingIgnoreCase(String autor);

    List<Livro> findByGeneroContainingIgnoreCase(String genero);

    List<Livro> findByDisponivelTrue();

    List<Livro> findByAvaliacaoMediaGreaterThanEqual(double avaliacao);

    List<Livro> findByPaginasBetween(int min, int max);

    // Ordenação
    List<Livro> findAllByOrderByAvaliacaoMediaDesc();

    List<Livro> findTop5ByOrderByAvaliacaoMediaDesc();
}