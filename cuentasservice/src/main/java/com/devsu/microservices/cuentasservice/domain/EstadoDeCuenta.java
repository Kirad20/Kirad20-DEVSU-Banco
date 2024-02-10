package com.devsu.microservices.cuentasservice.domain;

import java.util.List;
@lombok.Value
public class EstadoDeCuenta {
    long cliente;
    String nombre;
    String rfc;
    String genero;
    String fechaNacimiento;
    String curp;
    String direccion;
    String telefono;
    List<CuentaConMovimientos> cuentas;

    public EstadoDeCuenta(Cliente cliente, List<CuentaConMovimientos> cuentas) {
        this.cliente = cliente.getId();
        this.rfc = cliente.getRfc();
        this.nombre = cliente.getNombre();
        this.genero = cliente.getGenero();
        this.fechaNacimiento = cliente.getFechaNacimiento();
        this.curp = cliente.getCurp();
        this.direccion = cliente.getDireccion();
        this.telefono = cliente.getTelefono();
        this.cuentas = cuentas;
    }
}
