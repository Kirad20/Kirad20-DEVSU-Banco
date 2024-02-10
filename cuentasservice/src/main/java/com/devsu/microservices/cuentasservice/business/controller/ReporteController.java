package com.devsu.microservices.cuentasservice.business.controller;

import com.devsu.microservices.cuentasservice.business.exception.MalformatedDataException;
import com.devsu.microservices.cuentasservice.business.service.impl.ReporteService;
import com.devsu.microservices.cuentasservice.domain.EstadoDeCuenta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/doc")
    public ResponseEntity<byte[]> getEstadoDeCuentaDoc(@RequestParam String fechaInicio, @RequestParam String fechaFin, @RequestParam Long numeroCliente) {
        try {
            byte[] reporte= reporteService.getReporteEstadoDeCuentasDoc(fechaInicio, fechaFin, numeroCliente);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "estado_de_cuenta.docx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(reporte);
        } catch (ParseException e) {
            throw new MalformatedDataException("La fecha no tiene el formato correcto (yyyy-MM-dd)");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<EstadoDeCuenta> getEstadoDeCuenta(@RequestParam String fechaInicio, @RequestParam String fechaFin, @RequestParam Long numeroCliente) {
        try {
            return ResponseEntity.ok(reporteService.getEstadoDeCuentas(fechaInicio, fechaFin, numeroCliente));
        } catch (ParseException e) {
            throw new MalformatedDataException("La fecha no tiene el formato correcto (yyyy-MM-dd)");
        }
    }

}
