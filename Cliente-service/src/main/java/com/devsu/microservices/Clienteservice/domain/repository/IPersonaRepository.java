package com.devsu.microservices.Clienteservice.domain.repository;

import com.devsu.microservices.Clienteservice.domain.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonaRepository extends JpaRepository<PersonaEntity, String> {
}
