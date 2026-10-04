package br.edu.infnet.skoob_api_ingridmunhoz.livro;

import br.edu.infnet.skoob_api_ingridmunhoz.exception.IdentificadorDuplicadoException;
import br.edu.infnet.skoob_api_ingridmunhoz.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    // CRUD
    public Livro incluir(Livro livro) {
        validarLivro(livro);
        return livroRepository.save(livro);
    }

    public Livro alterar(Livro livro) {
        Livro existente = obterPorId(livro.getId());

        if (livro.getIsbn() != null && !livro.getIsbn().isEmpty()
                && !livro.getIsbn().equals(existente.getIsbn())
                && livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new IdentificadorDuplicadoException(
                    "ISBN '" + livro.getIsbn() + "' já está cadastrado!");
        }

        return livroRepository.save(livro);
    }


    public void excluir(Long id) {
        verificarExistencia(id);
        livroRepository.deleteById(id);
    }

    public Livro obterPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhum livro encontrado para o identificador " + id));
    }

    public List<Livro> obterLista() {
        return livroRepository.findAll();
    }

    // Validações
    private void validarLivro(Livro livro) {
        if (livro == null) {
            throw new IllegalArgumentException("Livro não pode ser nulo!");
        }
        if (livro.getIsbn() != null && !livro.getIsbn().isEmpty()
                && livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new IdentificadorDuplicadoException(
                    "ISBN '" + livro.getIsbn() + "' já está cadastrado!");
        }
    }

    private void verificarExistencia(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Identificador não pode ser nulo!");
        }
        if (!livroRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Nenhum livro encontrado para o identificador " + id);
        }
    }

    // Consultas com Spring Data
    public List<Livro> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return obterLista();
        }
        return livroRepository.findByTituloContainingIgnoreCase(titulo.trim());
    }

    public List<Livro> buscarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            return obterLista();
        }
        return livroRepository.findByAutorContainingIgnoreCase(autor.trim());
    }

    public List<Livro> buscarPorGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            return obterLista();
        }
        return livroRepository.findByGeneroContainingIgnoreCase(genero.trim());
    }

    public List<Livro> listarDisponiveis() {
        return livroRepository.findByDisponivelTrue();
    }

    public List<Livro> listarPorAvaliacao() {
        return livroRepository.findAllByOrderByAvaliacaoMediaDesc();
    }

    public List<Livro> listarTop5MelhorAvaliados() {
        return livroRepository.findTop5ByOrderByAvaliacaoMediaDesc();
    }

    public List<Livro> listarPorFaixaDePaginas(int min, int max) {
        return livroRepository.findByPaginasBetween(min, max);
    }

    public List<Livro> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return obterLista();
        }

        String termoLower = termo.trim().toLowerCase();
        // Combina duas consultas
        List<Livro> porTitulo = buscarPorTitulo(termoLower);
        List<Livro> porAutor = buscarPorAutor(termoLower);

        // Remove duplicatas (Stream)
        porTitulo.addAll(porAutor);
        return porTitulo.stream().distinct().collect(java.util.stream.Collectors.toList());
    }

    public double calcularMediaPaginas() {
        return livroRepository.findAll().stream()
                .mapToInt(Livro::getPaginas)
                .average()
                .orElse(0.0);
    }
}