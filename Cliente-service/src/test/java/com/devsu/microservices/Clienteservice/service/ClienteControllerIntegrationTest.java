package com.devsu.microservices.Clienteservice.service;

import com.devsu.microservices.Clienteservice.domain.entity.ClienteEntity;
import com.devsu.microservices.Clienteservice.domain.entity.PersonaEntity;
import com.devsu.microservices.Clienteservice.domain.repository.IClienteRepository;
import com.devsu.microservices.Clienteservice.domain.repository.IPersonaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = {
                "spring.datasource.url=localhost:3306/banco_db",
                "spring.datasource.username=root",
                "spring.datasource.password=password",
                "spring.rabbitmq.host=localhost",
                "spring.rabbitmq.port=5672",
                "spring.rabbitmq.username=guest",
                "spring.rabbitmq.password=guest",
                "server.port=8002",
                "eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka",
        }
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Test
    public void obtenerClientePorId_DebeRetornarClienteExistente() throws Exception {

        PersonaEntity persona = new PersonaEntity("moec96", "Mauricio", "Masculino", "20/04/1996","moec349","direccion 1","1234567");
        personaRepository.save(persona);
        ClienteEntity cliente = new ClienteEntity(1234, "1234", true,persona);
        clienteRepository.save(cliente);

        mockMvc.perform(get("/clientes/{id}", cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rfc", is(persona.getRfc())))
                .andExpect(jsonPath("$.nombre", is(persona.getNombre())))
                .andExpect(jsonPath("$.estado", is(cliente.isEstado())));
    }
}
