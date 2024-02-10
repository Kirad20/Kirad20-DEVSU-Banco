package com.devsu.microservices.cuentasservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {
    private long numeroCuenta;
    private double saldo;
    private boolean estado;
    private String tipoCuenta;
    private String nombreCliente;
    private Long idCliente;
}
