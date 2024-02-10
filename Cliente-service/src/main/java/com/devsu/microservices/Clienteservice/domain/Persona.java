package com.devsu.microservices.Clienteservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    private String rfc;
    private String nombre;
    private String genero;
    private String fechaNacimiento;
    private String curp;
    private String direccion;
    private String telefono;
}
