package com.devsu.microservices.cuentasservice.business.service;

import com.devsu.microservices.cuentasservice.domain.Cliente;

public interface IClienteService {
    Cliente findById(long idCliente);
}
