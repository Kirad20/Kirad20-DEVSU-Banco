package com.devsu.microservices.cuentasservice.business.service;

import com.devsu.microservices.cuentasservice.domain.Cuenta;
import com.devsu.microservices.cuentasservice.domain.entity.CuentaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ICuentaService {
    Cuenta findById(Long id);

    List<Cuenta> getCuentas();

    List<Cuenta> getCuentasDeCliente(Long idCliente);

    Cuenta findByIdCuenta(Long idCuenta);

    Cuenta createCuenta(Cuenta cuenta);

    Cuenta updateCuenta(Long idCuenta, Cuenta cuenta) throws JsonProcessingException;

    void deleteCuenta(Long idCuenta);
}
