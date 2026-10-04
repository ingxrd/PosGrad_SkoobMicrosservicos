package br.edu.infnet.skoob_api_ingridmunhoz.config;

import br.edu.infnet.skoob_api_ingridmunhoz.livro.Livro;
import br.edu.infnet.skoob_api_ingridmunhoz.livro.batch.LivroCsv;
import br.edu.infnet.skoob_api_ingridmunhoz.livro.batch.LivroCsvProcessor;
import br.edu.infnet.skoob_api_ingridmunhoz.livro.batch.LivroItemWriter;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.ChunkOrientedStepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    public static final int CHUNK_SIZE = 5;

    @Bean
    public FlatFileItemReader<LivroCsv> livroReader() {
        return new FlatFileItemReaderBuilder<LivroCsv>()
                .name("livroReader")
                .resource(new ClassPathResource("livros_importacao.csv"))
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("titulo", "autor", "isbn", "editora", "paginas",
                        "genero", "avaliacaoMedia", "disponivel", "sinopse")
                .targetType(LivroCsv.class)
                .build();
    }

    @Bean
    public ItemProcessor<LivroCsv, Livro> livroProcessor(LivroCsvProcessor processor) {
        return processor;
    }

    @Bean
    public ItemWriter<Livro> livroWriter(LivroItemWriter writer) {
        return writer;
    }

    @Bean
    public Step importarLivrosStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   ItemReader<LivroCsv> livroReader,
                                   ItemProcessor<LivroCsv, Livro> livroProcessor,
                                   ItemWriter<Livro> livroWriter) {
        return new ChunkOrientedStepBuilder<LivroCsv, Livro>(
                jobRepository, CHUNK_SIZE)
                .reader(livroReader)
                .processor(livroProcessor)
                .writer(livroWriter)
                .build();
    }

    @Bean
    public Job importarLivrosJob(JobRepository jobRepository, Step importarLivrosStep) {
        return new JobBuilder("importarLivrosJob", jobRepository)
                .start(importarLivrosStep)
                .build();
    }
}