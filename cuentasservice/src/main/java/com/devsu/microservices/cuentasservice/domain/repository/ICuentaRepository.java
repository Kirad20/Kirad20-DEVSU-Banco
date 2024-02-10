package com.devsu.microservices.cuentasservice.domain.repository;

import com.devsu.microservices.cuentasservice.domain.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICuentaRepository extends JpaRepository<CuentaEntity, Long> {
    List<CuentaEntity> findByIdCliente(Long idCliente);
}
