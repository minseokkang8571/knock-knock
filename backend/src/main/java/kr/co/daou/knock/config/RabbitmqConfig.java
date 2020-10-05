package kr.co.daou.knock.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    private static final String QUEUE_NAME_CHAT = "KnockChat";
    private static final String QUEUE_NAME_CODE = "KnockCode";

    private static final String EXCHANGE_NAME = "spring-boot-exchange";
    private static final String CHAT_ROUTING_KEY = "chat";
    private static final String CODE_ROUTING_KEY = "code";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME_CHAT, true);
    }

    @Bean
    Queue queueCode() {
        return new Queue(QUEUE_NAME_CODE, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CHAT_ROUTING_KEY);
    }

    @Bean
    Binding bindingCode(Queue queueCode, TopicExchange exchange) {
        return BindingBuilder.bind(queueCode).to(exchange).with(CODE_ROUTING_KEY);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                  MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
