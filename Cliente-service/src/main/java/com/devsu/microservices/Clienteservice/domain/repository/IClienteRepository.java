package com.devsu.microservices.Clienteservice.domain.repository;

import com.devsu.microservices.Clienteservice.domain.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
