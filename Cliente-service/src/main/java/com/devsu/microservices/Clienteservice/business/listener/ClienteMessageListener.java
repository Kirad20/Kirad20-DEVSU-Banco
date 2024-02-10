package com.devsu.microservices.Clienteservice.business.listener;

import com.devsu.microservices.Clienteservice.business.service.IClienteService;
import com.devsu.microservices.Clienteservice.domain.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClienteMessageListener {

    private final IClienteService clienteService;

    public ClienteMessageListener(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RabbitListener(queues = "cliente-queue")
    public void recibirCliente(String clienteString) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Cliente cliente = objectMapper.readValue(clienteString, Cliente.class);
            clienteService.actualizarCliente(cliente.getId(), cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
