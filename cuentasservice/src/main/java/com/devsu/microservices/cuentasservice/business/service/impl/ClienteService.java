package com.devsu.microservices.cuentasservice.business.service.impl;

import com.devsu.microservices.cuentasservice.business.service.IClienteService;
import com.devsu.microservices.cuentasservice.domain.Cliente;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ClienteService implements IClienteService {
    @Override
    public Cliente findById(long idCliente) {
        RestTemplate restTemplate = new RestTemplate();
        String usuarioUrl = "http://gateway:7000/clientes/{clienteId}"; // URL del endpoint de usuarios
        ResponseEntity<Cliente> response = restTemplate.exchange(usuarioUrl, HttpMethod.GET, null, Cliente.class, idCliente);
        return response.getBody();
    }
}
