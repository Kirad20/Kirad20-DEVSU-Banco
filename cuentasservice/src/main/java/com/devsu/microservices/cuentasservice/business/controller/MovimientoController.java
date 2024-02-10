package com.devsu.microservices.cuentasservice.business.controller;

import com.devsu.microservices.cuentasservice.business.exception.ResourceNotFoundException;
import com.devsu.microservices.cuentasservice.business.service.impl.MovimientoService;
import com.devsu.microservices.cuentasservice.domain.Movimiento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping()
    private ResponseEntity<List<Movimiento>> getMovimientos() {
        return ResponseEntity.ok(movimientoService.getMovimientos());
    }
    @GetMapping("{idMovimiento}")
    private ResponseEntity<Movimiento> getMovimientoById(@PathVariable long idMovimiento) {
        return ResponseEntity.ok(movimientoService.findByIdMovimiento(idMovimiento));
    }

    @PostMapping()
    private  ResponseEntity<Movimiento> createMovimiento(@RequestBody Movimiento movimiento) {
        Movimiento movimientoCreated = movimientoService.createMovimiento(movimiento);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(movimientoCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(movimientoCreated);
    }

    @PutMapping("/{idMovimiento}")
    private ResponseEntity<Movimiento> updateMovimiento(@PathVariable long idMovimiento, @RequestBody Movimiento movimiento) {
        return ResponseEntity.ok(movimientoService.updateMovimiento(idMovimiento, movimiento));
    }

    @DeleteMapping("/{idMovimiento}")
    private ResponseEntity<Void> deleteMovimiento(@PathVariable long idMovimiento) {
        boolean existMovimiento = movimientoService.existMovimientoById(idMovimiento);
        if (!existMovimiento) {
            throw new ResourceNotFoundException("Movimiento no encontrado");
        }
        movimientoService.deleteMovimiento(idMovimiento);
        return ResponseEntity.noContent().build();
    }
}
