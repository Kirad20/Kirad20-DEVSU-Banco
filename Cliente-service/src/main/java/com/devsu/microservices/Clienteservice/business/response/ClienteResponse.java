package com.devsu.microservices.Clienteservice.business.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
    private String id;
    private String contrasena;
    private boolean estado;
}
