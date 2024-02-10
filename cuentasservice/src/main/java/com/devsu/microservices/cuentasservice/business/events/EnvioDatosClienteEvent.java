package com.devsu.microservices.cuentasservice.business.events;

import com.devsu.microservices.cuentasservice.domain.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvioDatosClienteEvent {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EnvioDatosClienteEvent(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void enviarCliente(Cliente cliente) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String clienteJson = objectMapper.writeValueAsString(cliente);
        rabbitTemplate.convertAndSend("cliente-queue", clienteJson);
    }
}
