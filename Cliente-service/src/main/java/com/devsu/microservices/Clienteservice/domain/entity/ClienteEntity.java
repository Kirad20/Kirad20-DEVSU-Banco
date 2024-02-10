package com.devsu.microservices.Clienteservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Table(name = "clientes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String contrasena;
    private boolean estado;
    @OneToOne()
    private PersonaEntity personaEntity;
}
