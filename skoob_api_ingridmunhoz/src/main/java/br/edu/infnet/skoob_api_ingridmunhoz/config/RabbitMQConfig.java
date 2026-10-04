package br.edu.infnet.skoob_api_ingridmunhoz.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NOTIFICACAO = "skoob.notificacao.comentario.queue";
    public static final String EXCHANGE = "skoob.exchange";
    public static final String ROUTING_KEY = "notificacao.comentario";

    @Bean
    public Queue filaNotificacaoComentario() {
        return QueueBuilder.durable(QUEUE_NOTIFICACAO).build();
    }

    @Bean
    public DirectExchange skoobExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingNotificacaoComentario(Queue filaNotificacaoComentario, DirectExchange skoobExchange) {
        return BindingBuilder.bind(filaNotificacaoComentario).to(skoobExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}