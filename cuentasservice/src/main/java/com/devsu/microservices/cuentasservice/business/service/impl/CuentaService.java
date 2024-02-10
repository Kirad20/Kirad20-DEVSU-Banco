package com.devsu.microservices.cuentasservice.business.service.impl;

import com.devsu.microservices.cuentasservice.business.events.EnvioDatosClienteEvent;
import com.devsu.microservices.cuentasservice.business.exception.ResourceNotFoundException;
import com.devsu.microservices.cuentasservice.business.service.ICuentaService;
import com.devsu.microservices.cuentasservice.business.service.IMovimientoService;
import com.devsu.microservices.cuentasservice.domain.Cliente;
import com.devsu.microservices.cuentasservice.domain.Cuenta;
import com.devsu.microservices.cuentasservice.domain.Movimiento;
import com.devsu.microservices.cuentasservice.domain.entity.CuentaEntity;
import com.devsu.microservices.cuentasservice.domain.repository.ICuentaRepository;
import com.devsu.microservices.cuentasservice.domain.util.TipoMovimientoEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService implements ICuentaService {
    private final ICuentaRepository cuentaRepository;

    private final IMovimientoService movimientoService;
    private final EnvioDatosClienteEvent envioDatosClienteEvent;
    private final ClienteService clienteService;
    public CuentaService(ICuentaRepository cuentaRepository, ClienteService clienteService, EnvioDatosClienteEvent envioDatosClienteEvent, IMovimientoService movimientoService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteService = clienteService;
        this.envioDatosClienteEvent = envioDatosClienteEvent;
        this.movimientoService = movimientoService;
    }

    @Override
    public Cuenta findById(Long id) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(id).orElse(null);

        Cliente datosCliente = clienteService.findById(cuentaEntity.getIdCliente());

        // Combinar los datos de la cuenta y del cliente en un DTO
       Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaEntity.getNumeroCuenta());
        cuenta.setSaldo(cuentaEntity.getSaldo());
        cuenta.setEstado(cuentaEntity.isEstado());
        cuenta.setTipoCuenta(cuentaEntity.getTipoCuenta());
        cuenta.setNombreCliente(datosCliente.getNombre());
        return cuenta;
    }

    @Override
    public List<Cuenta> getCuentas() {
        List<CuentaEntity> cuentas = cuentaRepository.findAll();
        return mapEntityListoToDomain(cuentas);
    }

    @Override
    public List<Cuenta> getCuentasDeCliente(Long idCliente) {
        List<CuentaEntity> cuentas = cuentaRepository.findByIdCliente(idCliente);
        return mapEntityListoToDomain(cuentas);
    }

    public List<Cuenta> mapEntityListoToDomain(List<CuentaEntity> cuentas) {
        return cuentas.stream().map(cuentaEntity -> {
            Cliente datosCliente = clienteService.findById(cuentaEntity.getIdCliente());
            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroCuenta(cuentaEntity.getNumeroCuenta());
            cuenta.setSaldo(cuentaEntity.getSaldo());
            cuenta.setEstado(cuentaEntity.isEstado());
            cuenta.setTipoCuenta(cuentaEntity.getTipoCuenta());
            cuenta.setNombreCliente(datosCliente.getNombre());
            return cuenta;
        }).toList();
    }

    @Override
    public Cuenta findByIdCuenta(Long idCuenta) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(idCuenta).orElseThrow(
                () -> new ResourceNotFoundException("Cuenta no encontrada")
        );
        Cliente datosCliente =Optional.of(clienteService.findById(cuentaEntity.getIdCliente())).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado")
        );
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaEntity.getNumeroCuenta());
        cuenta.setSaldo(cuentaEntity.getSaldo());
        cuenta.setEstado(cuentaEntity.isEstado());
        cuenta.setTipoCuenta(cuentaEntity.getTipoCuenta());
        cuenta.setNombreCliente(datosCliente.getNombre());
        return cuenta;
    }

    @Override
    @Transactional
    public Cuenta createCuenta(Cuenta cuenta) {
        CuentaEntity cuentaEntity = new CuentaEntity();
        cuentaEntity.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaEntity.setSaldo(cuenta.getSaldo());
        cuentaEntity.setEstado(cuenta.isEstado());
        cuentaEntity.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaEntity.setIdCliente(cuenta.getIdCliente());
        CuentaEntity saved =cuentaRepository.save(cuentaEntity);
        cuenta.setNumeroCuenta(saved.getNumeroCuenta());
        return cuenta;
    }

    @Override
    public Cuenta updateCuenta(Long idCuenta, Cuenta cuenta) throws JsonProcessingException {
        CuentaEntity cuentaEntity = cuentaRepository.findById(idCuenta).orElseThrow(
                () -> new ResourceNotFoundException("Cuenta no encontrada")
        );
        cuentaEntity.setSaldo(cuenta.getSaldo());
        cuentaEntity.setEstado(cuenta.isEstado());
        cuentaEntity.setTipoCuenta(cuenta.getTipoCuenta());
        Cliente datosCliente = Optional.of(clienteService.findById(cuentaEntity.getIdCliente())).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado")
        );
        if (cuenta.getNombreCliente().isBlank()){
            cuenta.setNombreCliente(datosCliente.getNombre());

        }else {
            datosCliente.setNombre(cuenta.getNombreCliente());
            envioDatosClienteEvent.enviarCliente(datosCliente);
        }
        cuentaRepository.save(cuentaEntity);

        return cuenta;
    }

    @Override
    public void deleteCuenta(Long idCuenta) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(idCuenta).orElseThrow(
                () -> new ResourceNotFoundException("Cuenta no encontrada")
        );
        cuentaRepository.deleteById(idCuenta);
    }
}
