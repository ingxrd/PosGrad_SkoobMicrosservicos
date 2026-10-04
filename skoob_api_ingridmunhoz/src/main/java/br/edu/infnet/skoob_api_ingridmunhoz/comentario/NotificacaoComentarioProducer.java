package br.edu.infnet.skoob_api_ingridmunhoz.comentario;

import br.edu.infnet.skoob_api_ingridmunhoz.comentario.dto.NotificacaoComentarioDTO;
import br.edu.infnet.skoob_api_ingridmunhoz.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoComentarioProducer {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoComentarioProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public NotificacaoComentarioProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicar(NotificacaoComentarioDTO dto) {
        log.info("[PRODUCER] Publicando mensagem na fila: {}", dto.getComentarioId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, dto);
    }
}