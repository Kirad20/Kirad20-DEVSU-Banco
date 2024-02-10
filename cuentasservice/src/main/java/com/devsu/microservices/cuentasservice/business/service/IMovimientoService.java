package com.devsu.microservices.cuentasservice.business.service;

import com.devsu.microservices.cuentasservice.domain.Movimiento;

import java.util.Date;
import java.util.List;

public interface IMovimientoService {
    List<Movimiento> getMovimientos();

    Movimiento findByIdMovimiento(long idMovimiento);

    Movimiento createMovimiento(Movimiento movimiento);

    Movimiento updateMovimiento(long idMovimiento, Movimiento movimiento);

    boolean existMovimientoById(long idMovimiento);

    void deleteMovimiento(long idMovimiento);

    List<Movimiento> getMovimientosByCuentaAndDate(long numeroCuenta, Date fechaInicio, Date fechaFin);
}
