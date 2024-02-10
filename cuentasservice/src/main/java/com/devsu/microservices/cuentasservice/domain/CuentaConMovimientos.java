package com.devsu.microservices.cuentasservice.domain;

import lombok.Data;

import java.util.List;

@Data
public class CuentaConMovimientos{
    private long numeroCuenta;
    private double saldo;
    private boolean estado;
    private String tipoCuenta;

    private List<Movimiento> movimientos;

    public CuentaConMovimientos(Cuenta cuenta, List<Movimiento> movimientos) {
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.saldo = cuenta.getSaldo();
        this.estado = cuenta.isEstado();
        this.tipoCuenta = cuenta.getTipoCuenta();
        this.movimientos = movimientos;
    }
}
