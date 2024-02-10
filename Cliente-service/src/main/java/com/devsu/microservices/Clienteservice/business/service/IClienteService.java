package com.devsu.microservices.Clienteservice.business.service;

import com.devsu.microservices.Clienteservice.domain.Cliente;

import java.util.List;

public interface IClienteService {

    Cliente createUsuario(Cliente usuario);

    List<Cliente> getAll();

    Cliente getClienteById(long idCliente);

    Boolean existById(Long idCliente);

    Cliente actualizarCliente(Long idUsuario, Cliente cliente);

    void eliminarCliente(Long idUsuario);
}
