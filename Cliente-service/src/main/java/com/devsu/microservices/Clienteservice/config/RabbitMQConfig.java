package com.devsu.microservices.Clienteservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue datosClienteQueue() {
        return new Queue("cliente-queue");
    }
}
