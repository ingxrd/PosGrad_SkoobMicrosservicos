package br.edu.infnet.skoob_api_ingridmunhoz.livro.batch;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/batch")
public class BatchController {

    private final JobOperator jobOperator;
    private final Job importarLivrosJob;

    public BatchController(JobOperator jobOperator, Job importarLivrosJob) {
        this.jobOperator = jobOperator;
        this.importarLivrosJob = importarLivrosJob;
    }

    @PostMapping("/importar-livros")
    public ResponseEntity<Map<String, String>> importarLivros() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            // JobOperator extends JobLauncher → mesmo método run()
            jobOperator.run(importarLivrosJob, params);

            return ResponseEntity.ok(Map.of(
                    "status", "SUCESSO",
                    "mensagem", "Job de importação de livros executado com sucesso"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "ERRO",
                    "mensagem", e.getMessage()
            ));
        }
    }
}