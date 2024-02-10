package com.devsu.microservices.Clienteservice.service;

import com.devsu.microservices.Clienteservice.business.service.IClienteService;
import com.devsu.microservices.Clienteservice.business.service.impl.ClienteServiceImpl;
import com.devsu.microservices.Clienteservice.domain.Cliente;
import com.devsu.microservices.Clienteservice.domain.entity.ClienteEntity;
import com.devsu.microservices.Clienteservice.domain.entity.PersonaEntity;
import com.devsu.microservices.Clienteservice.domain.repository.IClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class ClienteServiceTest {

    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    public void obtenerClientePorId_DebeRetornarClienteExistente() {

        Long clienteId = 1L;
        PersonaEntity personaMock = new PersonaEntity("moec96", "Mauricio", "Masculino", "20/04/1996","moec349","direccion 1","1234567");
        ClienteEntity clienteMock = new ClienteEntity(clienteId, "John Doe", true,personaMock);
        Mockito.when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

        Cliente resultado = clienteService.getClienteById(clienteId);

        assertNotNull(resultado);
        assertEquals(clienteId, resultado.getId());
        assertEquals("Mauricio", resultado.getNombre());
        assertEquals("moec96", resultado.getRfc());
        assertTrue(resultado.isEstado());
    }
}
