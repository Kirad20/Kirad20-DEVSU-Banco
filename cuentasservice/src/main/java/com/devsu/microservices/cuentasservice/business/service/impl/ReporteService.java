package com.devsu.microservices.cuentasservice.business.service.impl;

import com.devsu.microservices.cuentasservice.business.service.IReporteService;
import com.devsu.microservices.cuentasservice.domain.*;
import com.devsu.microservices.cuentasservice.domain.util.DateUtil;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class ReporteService implements IReporteService {

    private final ClienteService clienteService;

    private final CuentaService cuentaService;

    private final MovimientoService movimientoService;

    public ReporteService(ClienteService clienteService, CuentaService cuentaService, MovimientoService movimientoService) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
    }

    public byte[] getReporteEstadoDeCuentasDoc(String fechaInicio, String fechaFin, long idCliente) throws ParseException, IOException {

        Date fechaInicioDate = DateUtil.convertStringToDate(fechaInicio, "yyyy-MM-dd");
        Date fechaFinDate = DateUtil.convertStringToDate(fechaFin, "yyyy-MM-dd");

        Cliente cliente = clienteService.findById(idCliente);
        List<Cuenta> cuentas = cuentaService.getCuentasDeCliente(idCliente);


        XWPFDocument document = new XWPFDocument();

        // Agregar el encabezado
        XWPFParagraph header = document.createParagraph();
        XWPFRun headerRun = header.createRun();
        headerRun.setText("Reporte de movimientos del cliente " + cliente.getNombre());
        headerRun.setBold(true);
        headerRun.setFontSize(14);


       for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoService.getMovimientosByCuentaAndDate(cuenta.getNumeroCuenta(), fechaInicioDate, fechaFinDate);

           XWPFParagraph accountTitle = document.createParagraph();
           XWPFRun accountTitleRun = accountTitle.createRun();
           accountTitleRun.setText("Cuenta: " + cuenta.getNumeroCuenta());
           accountTitleRun.setBold(true);
           accountTitleRun.setFontSize(12);

           XWPFTable table = document.createTable();
           XWPFTableRow headerRow = table.getRow(0);
           headerRow.getCell(0).setText("Tipo");
           headerRow.addNewTableCell().setText("Monto");
           headerRow.addNewTableCell().setText("Saldo");
           headerRow.addNewTableCell().setText("Fecha");
           for (Movimiento movimiento : movimientos) {
               XWPFTableRow row = table.createRow();
               row.getCell(0).setText(movimiento.getTipoMovimiento().toString());
               row.getCell(1).setText(String.valueOf(movimiento.getMonto()));
                row.getCell(2).setText(String.valueOf(movimiento.getSaldo()));
               row.getCell(3).setText(movimiento.getFechaMovimiento().toString());
           }

           document.createParagraph();

        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        return outputStream.toByteArray();
    }

    public EstadoDeCuenta getEstadoDeCuentas(String fechaInicio, String fechaFin, Long idCliente) throws ParseException {
        Date fechaInicioDate = DateUtil.convertStringToDate(fechaInicio, "yyyy-MM-dd");
        Date fechaFinDate = DateUtil.convertStringToDate(fechaFin, "yyyy-MM-dd");

        Cliente cliente = clienteService.findById(idCliente);
        List<Cuenta> cuentas = cuentaService.getCuentasDeCliente(idCliente);

        List<CuentaConMovimientos> cuentasConMovimientos = cuentas.stream().map(cuenta -> {
            List<Movimiento> movimientos = movimientoService.getMovimientosByCuentaAndDate(cuenta.getNumeroCuenta(), fechaInicioDate, fechaFinDate);
            return new CuentaConMovimientos(cuenta, movimientos);
        }).toList();
        return new EstadoDeCuenta(cliente, cuentasConMovimientos);

    }
}
