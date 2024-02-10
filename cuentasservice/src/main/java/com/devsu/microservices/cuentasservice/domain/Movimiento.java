package com.devsu.microservices.cuentasservice.domain;

import com.devsu.microservices.cuentasservice.domain.util.TipoMovimientoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {

    public Movimiento(double monto, TipoMovimientoEnum tipoMovimiento, long numeroCuenta){
        this.monto = monto;
        this.tipoMovimiento = tipoMovimiento;
        this.numeroCuenta = numeroCuenta;
    }
    private long id;
    private double monto;
    private double saldo;
    private TipoMovimientoEnum tipoMovimiento;
    @CreationTimestamp
    private Date fechaMovimiento;
    private long numeroCuenta;
}
