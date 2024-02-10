package com.devsu.microservices.cuentasservice.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity @Data
@Table(name = "movimientos")
public class MovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double monto;
    private double saldo;
    private String tipoMovimiento;
    @CreationTimestamp
    private Date fechaMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_cuenta")
    private CuentaEntity cuenta;
}
