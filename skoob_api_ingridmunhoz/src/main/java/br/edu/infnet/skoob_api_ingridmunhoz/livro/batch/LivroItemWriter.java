package br.edu.infnet.skoob_api_ingridmunhoz.livro.batch;

import br.edu.infnet.skoob_api_ingridmunhoz.livro.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.livro.LivroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class LivroItemWriter implements ItemWriter<Livro> {

    private static final Logger log = LoggerFactory.getLogger(LivroItemWriter.class);
    private final LivroService livroService;

    public LivroItemWriter(LivroService livroService) {
        this.livroService = livroService;
    }

    @Override
    public void write(Chunk<? extends Livro> chunk) {
        for (Livro livro : chunk) {
            try {
                livroService.incluir(livro);
                log.info("[BATCH] Livro gravado no banco: {} (id={})", livro.getTitulo(), livro.getId());
            } catch (Exception e) {
                log.warn("[BATCH] Livro ignorado (já existe?): {} - {}", livro.getTitulo(), e.getMessage());
            }
        }
    }
}