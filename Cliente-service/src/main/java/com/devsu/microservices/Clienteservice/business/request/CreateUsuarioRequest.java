package com.devsu.microservices.Clienteservice.business.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsuarioRequest {
    private String rfc;
    private String nombre;
    private String genero;
    private String fechaNacimiento;
    private String curp;
    private String direccion;
    private String telefono;
    private String contrasena;
    private boolean estado;
}
