package br.edu.infnet.skoob_api_ingridmunhoz.livro.batch;

import br.edu.infnet.skoob_api_ingridmunhoz.livro.Livro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class LivroCsvProcessor implements ItemProcessor<LivroCsv, Livro> {

    private static final Logger log = LoggerFactory.getLogger(LivroCsvProcessor.class);

    @Override
    public Livro process(LivroCsv item) {
        // Regra 1: descartar registros sem título ou sem autor
        if (item.getTitulo() == null || item.getTitulo().isBlank()
                || item.getAutor() == null || item.getAutor().isBlank()) {
            log.warn("[BATCH] Registro descartado (título/autor vazio): {}", item);
            return null; // retornar null faz o Spring Batch pular este item
        }

        // Regra 2: montar a entidade Livro com dados normalizados
        Livro livro = new Livro();
        livro.setTitulo(item.getTitulo().trim());
        livro.setAutor(item.getAutor().trim());
        livro.setIsbn(item.getIsbn() != null ? item.getIsbn().trim() : null);
        livro.setEditora(item.getEditora() != null ? item.getEditora().trim() : null);
        livro.setPaginas(item.getPaginas() != null ? item.getPaginas() : 0);
        livro.setGenero(item.getGenero() != null ? item.getGenero().trim() : "Não informado");
        livro.setAvaliacaoMedia(item.getAvaliacaoMedia() != null ? item.getAvaliacaoMedia() : 0.0);
        livro.setDisponivel(item.getDisponivel() != null ? item.getDisponivel() : true);
        livro.setSinopse(item.getSinopse());

        log.info("[BATCH] Processando livro: {} - {}", livro.getTitulo(), livro.getAutor());
        return livro;
    }
}