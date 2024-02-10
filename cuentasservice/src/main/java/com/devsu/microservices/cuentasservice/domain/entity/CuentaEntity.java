package com.devsu.microservices.cuentasservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity @Data
@Table(name = "cuentas")
public class CuentaEntity {
    @Id
    @Column(name = "numero_cuenta")
    private long numeroCuenta;
    private long idCliente;
    private double saldo;
    private boolean estado;
    private String tipoCuenta;

    @OneToMany(mappedBy = "cuenta")
    List<MovimientoEntity> movimientos;
}
