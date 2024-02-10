package com.devsu.microservices.Clienteservice.business.service.impl;

import com.devsu.microservices.Clienteservice.business.exceptions.ResourceNotFoundException;
import com.devsu.microservices.Clienteservice.business.service.IClienteService;
import com.devsu.microservices.Clienteservice.domain.Cliente;
import com.devsu.microservices.Clienteservice.domain.entity.ClienteEntity;
import com.devsu.microservices.Clienteservice.domain.entity.PersonaEntity;
import com.devsu.microservices.Clienteservice.domain.repository.IClienteRepository;
import com.devsu.microservices.Clienteservice.domain.repository.IPersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {
    private final IClienteRepository clienteRepository;
    private final IPersonaRepository personaRepository;


    public ClienteServiceImpl(IClienteRepository clienteRepository, IPersonaRepository personaRepository) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
    }

    public void deleteClienteById(Long id) {
            clienteRepository.deleteById(id);
        }

    @Override
    @Transactional
    public Cliente createUsuario(Cliente usuario) {
        ClienteEntity toSave = new ClienteEntity();
        toSave.setContrasena(usuario.getContrasenia());
        toSave.setEstado(usuario.isEstado());

        PersonaEntity persona = new PersonaEntity();
        persona.setRfc(usuario.getRfc());
        persona.setCurp(usuario.getCurp());
        persona.setDireccion(usuario.getDireccion());
        persona.setFechaNacimiento(usuario.getFechaNacimiento());
        persona.setGenero(usuario.getGenero());
        persona.setNombre(usuario.getNombre());
        persona.setTelefono(usuario.getTelefono());

        PersonaEntity savedPersona = personaRepository.save(persona);
        toSave.setPersonaEntity(savedPersona);
        ClienteEntity clienteSaved = clienteRepository.save(toSave);

        usuario.setId(clienteSaved.getId());
        return usuario;
    }

    @Override
    public List<Cliente> getAll() {
        List<ClienteEntity> clientes = clienteRepository.findAll();
        return clientes.stream().map(this::mapEntityToCliente).toList();
    }

    @Override
    public Cliente getClienteById(long idCliente) {
        Optional<ClienteEntity> clienteEntity = clienteRepository.findById(idCliente);
        return clienteEntity.map(this::mapEntityToCliente).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado")
        );
    }

    @Override
    public Boolean existById(Long idCliente) {
        return clienteRepository.existsById(idCliente);
    }

    @Override
    public Cliente actualizarCliente(Long idCliente, Cliente cliente) {
        ClienteEntity clienteEntity = clienteRepository.findById(idCliente).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado")
        );
        clienteEntity.setContrasena(cliente.getContrasenia());
        clienteEntity.setEstado(cliente.isEstado());

        PersonaEntity persona = clienteEntity.getPersonaEntity();
        persona.setNombre(cliente.getNombre());
        persona.setTelefono(cliente.getTelefono());
        persona.setCurp(cliente.getCurp());
        persona.setDireccion(cliente.getDireccion());
        persona.setFechaNacimiento(cliente.getFechaNacimiento());
        persona.setGenero(cliente.getGenero());

        personaRepository.save(persona);

        clienteEntity.setPersonaEntity(persona);

        clienteRepository.save(clienteEntity);
        return this.mapEntityToCliente(clienteEntity);
    }

    @Override
    public void eliminarCliente(Long idUsuario) {
        clienteRepository.deleteById(idUsuario);
    }

    private Cliente mapEntityToCliente(ClienteEntity entity){
        Cliente cliente = new Cliente();
        //Datos personales
        PersonaEntity personaEntity = entity.getPersonaEntity();
        cliente.setNombre(personaEntity.getNombre());
        cliente.setDireccion(personaEntity.getDireccion());
        cliente.setTelefono(personaEntity.getTelefono());
        cliente.setRfc(personaEntity.getRfc());
        cliente.setCurp(personaEntity.getCurp());
        cliente.setGenero(personaEntity.getGenero());
        cliente.setFechaNacimiento(personaEntity.getFechaNacimiento());

        //Datos del cliente
        cliente.setId(entity.getId());
        cliente.setContrasenia(entity.getContrasena());
        cliente.setEstado(entity.isEstado());
        return cliente;
    }
}
