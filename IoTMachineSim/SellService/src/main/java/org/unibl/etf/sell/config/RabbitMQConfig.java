package org.unibl.etf.sell.config;

import java.io.File;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.queue-name}")
    private String queueName;

    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;
    
    @Value("${order.directory}")
    private String orderDirectory;
    
    @Bean
    Exchange declareExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Queue declareQueue() {
        return new Queue(queueName);
    }

    @Bean
    Binding declareBinding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
    
    @Bean
    Integer nextFileNumber() {
    	return new File(orderDirectory).listFiles().length + 1;
    }
    
    @Bean
    Gson jsonParser() {
    	return new Gson();
    }
}
