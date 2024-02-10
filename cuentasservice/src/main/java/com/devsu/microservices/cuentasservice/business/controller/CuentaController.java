package com.devsu.microservices.cuentasservice.business.controller;

import com.devsu.microservices.cuentasservice.business.service.impl.CuentaService;
import com.devsu.microservices.cuentasservice.domain.Cuenta;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping()
    private ResponseEntity<List<Cuenta>> getCuentasByClienteId() {
        return ResponseEntity.ok(cuentaService.getCuentas());
    }

    @GetMapping("/{idCuenta}")
    private ResponseEntity<Cuenta> getCuentaById(@PathVariable Long idCuenta) {
        return ResponseEntity.ok(cuentaService.findByIdCuenta(idCuenta));
    }

    @PostMapping()
    private ResponseEntity<Cuenta> post(@RequestBody Cuenta cuenta){
        Cuenta cuentaResponse = cuentaService.createCuenta( cuenta);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idCuenta}")
                .buildAndExpand(cuentaResponse.getNumeroCuenta())
                .toUri();
        return ResponseEntity.created(location).body(cuentaResponse);
    }

    @PutMapping("/{idCuenta}")
    private ResponseEntity<Cuenta> put(@PathVariable Long idCuenta, @RequestBody Cuenta cuenta){
        try {
            return ResponseEntity.ok(cuentaService.updateCuenta(idCuenta, cuenta));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{idCuenta}")
    private ResponseEntity<Void> delete(@PathVariable Long idCuenta){
        cuentaService.deleteCuenta(idCuenta);
        return ResponseEntity.noContent().build();
    }
}
