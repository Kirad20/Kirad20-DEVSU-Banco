package com.devsu.microservices.Clienteservice.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Table(name = "personas")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PersonaEntity {
    @Id
    private String rfc;
    private String nombre;
    private String genero;
    private String fechaNacimiento;
    private String curp;
    private String direccion;
    private String telefono;
}
