package com.devsu.microservices.Clienteservice.business.controller;

import com.devsu.microservices.Clienteservice.domain.Cliente;
import com.devsu.microservices.Clienteservice.business.exceptions.ResourceNotFoundException;
import com.devsu.microservices.Clienteservice.business.service.IClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final IClienteService clienteService;

    public ClienteController(@Autowired IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Cliente> clientes = clienteService.getAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> get(@PathVariable long idCliente){
        Cliente cliente =clienteService.getClienteById(idCliente);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> post(@RequestBody Cliente request) {
        Cliente response = clienteService.createUsuario(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Cliente>update(@PathVariable Long idUsuario, @RequestBody Cliente cliente){
        boolean existCliente =clienteService.existById(idUsuario);
        if (!existCliente){
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        Cliente clienteUpdated = clienteService.actualizarCliente(idUsuario, cliente);
        return ResponseEntity.ok(clienteUpdated);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void>delete(@PathVariable Long idUsuario){
        boolean existCliente =clienteService.existById(idUsuario);
        if (!existCliente){
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        clienteService.eliminarCliente(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
