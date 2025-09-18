package com.medkissi.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queueName;
    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routing.key.name}")
    private String routingKey;
    @Value("${rabbitmq.routing.key.json.name}")
    private String routingJsonKey;

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }


    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // binding between queue and exchange using a rounting
    @Bean
    public Binding binding() {
        return BindingBuilder.
                bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    //Binding between json queue and exchange using routing key

    @Bean
    public Binding jsonBinding() {

        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
