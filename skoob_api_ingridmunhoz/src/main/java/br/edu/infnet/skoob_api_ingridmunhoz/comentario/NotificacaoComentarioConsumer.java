package br.edu.infnet.skoob_api_ingridmunhoz.comentario;

import br.edu.infnet.skoob_api_ingridmunhoz.comentario.dto.NotificacaoComentarioDTO;
import br.edu.infnet.skoob_api_ingridmunhoz.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoComentarioConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoComentarioConsumer.class);

    // ======================================================================
    // TESTE DO CONSUMIDOR INDISPONÍVEL (exigido pelo enunciado da Etapa 4)
    //
    // Para SIMULAR o consumidor fora do ar:
    //   1. Comente a linha abaixo:  @RabbitListener(...)
    //   2. Faça:  docker compose up -d --build skoob-api
    //   3. Poste 2 ou 3 comentários pelo Swagger
    //   4. Veja as mensagens acumulando na fila em http://localhost:15672
    //   5. Descomente a linha abaixo e rebuilda de novo
    //   6. Veja as mensagens acumuladas sendo processadas de uma vez
    // ======================================================================
   @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICACAO)
    public void processar(NotificacaoComentarioDTO dto) {
        log.info("=================================================");
        log.info("[CONSUMER] Mensagem recebida!");
        log.info("[CONSUMER] Tipo: {}", dto.getTipo());
        log.info("[CONSUMER] Comentário ID: {}", dto.getComentarioId());
        log.info("[CONSUMER] Livro: {} (id={})", dto.getTituloLivro(), dto.getLivroId());
        log.info("[CONSUMER] Autor do comentário: {} (id={})",
                dto.getNomeAutorComentario(), dto.getUsuarioAutorComentarioId());
        log.info("[CONSUMER] Simulando envio de notificação ao autor do livro...");

        try {
            // Simula o tempo de processamento (envio de e-mail, push, etc.)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("[CONSUMER] Processamento interrompido!");
        }

        log.info("[CONSUMER] Notificação processada com sucesso!");
        log.info("=================================================");
    }
}