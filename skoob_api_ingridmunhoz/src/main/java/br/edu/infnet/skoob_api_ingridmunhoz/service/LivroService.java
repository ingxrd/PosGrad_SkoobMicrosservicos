package br.edu.infnet.skoob_api_ingridmunhoz.service;

import br.edu.infnet.skoob_api_ingridmunhoz.model.domain.Livro;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class LivroService extends BaseService<Livro> {

    @Override
    protected Long getId(Livro livro) {
        return livro != null ? (long) livro.getId() : null;
    }

    @Override
    protected void setId(Livro livro, Long id) {
        if (livro != null) {
            livro.setId(id.intValue());
        }
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    /**
     * Busca livros por título (case insensitive).
     */
    public List<Livro> buscarPorTitulo(String titulo) {
        return filtrarPorTexto(titulo, Livro::getTitulo);
    }

    /**
     * Busca livros por autor (case insensitive).
     */
    public List<Livro> buscarPorAutor(String autor) {
        return filtrarPorTexto(autor, Livro::getAutor);
    }

    /**
     * Busca livros por gênero.
     */
    public List<Livro> buscarPorGenero(String genero) {
        return filtrarPorTexto(genero, Livro::getGenero);
    }

    /**
     * Lista livros ordenados por avaliação (do maior para o menor).
     * Exemplo de ordenação com Comparator.
     */
    public List<Livro> listarPorAvaliacao() {
        return ordenarPor(Comparator.comparing(Livro::getAvaliacaoMedia).reversed());
    }

    /**
     * Lista livros disponíveis para empréstimo.
     */
    public List<Livro> listarDisponiveis() {
        return obterLista().stream()
                .filter(Livro::isDisponivel)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Lista livros por faixa de páginas.
     * Exemplo de filtro com range.
     */
    public List<Livro> listarPorFaixaDePaginas(int min, int max) {
        return obterLista().stream()
                .filter(l -> l.getPaginas() >= min && l.getPaginas() <= max)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Lista os 5 livros mais bem avaliados.
     * Exemplo de Stream com limit().
     */
    public List<Livro> listarTop5MelhorAvaliados() {
        return obterLista().stream()
                .sorted(Comparator.comparing(Livro::getAvaliacaoMedia).reversed())
                .limit(5)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Calcula a média de páginas de todos os livros.
     * Exemplo de Stream com mapToDouble e average.
     */
    public double calcularMediaPaginas() {
        return obterLista().stream()
                .mapToInt(Livro::getPaginas)
                .average()
                .orElse(0.0);
    }

    /**
     * Busca livros por termo (título ou autor).
     * Exemplo de busca com múltiplos critérios.
     */
    public List<Livro> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return obterLista();
        }

        String termoLower = termo.toLowerCase().trim();

        return obterLista().stream()
                .filter(livro ->
                        (livro.getTitulo() != null && livro.getTitulo().toLowerCase().contains(termoLower)) ||
                                (livro.getAutor() != null && livro.getAutor().toLowerCase().contains(termoLower))
                )
                .collect(java.util.stream.Collectors.toList());
    }
}