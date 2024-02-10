package com.devsu.microservices.cuentasservice.business.service.impl;

import com.devsu.microservices.cuentasservice.business.exception.AccountInsufficeFundsException;
import com.devsu.microservices.cuentasservice.business.exception.ResourceNotFoundException;
import com.devsu.microservices.cuentasservice.business.service.IMovimientoService;
import com.devsu.microservices.cuentasservice.domain.Movimiento;
import com.devsu.microservices.cuentasservice.domain.entity.CuentaEntity;
import com.devsu.microservices.cuentasservice.domain.entity.MovimientoEntity;
import com.devsu.microservices.cuentasservice.domain.repository.ICuentaRepository;
import com.devsu.microservices.cuentasservice.domain.repository.IMovimientoRepository;
import com.devsu.microservices.cuentasservice.domain.util.TipoMovimientoEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MovimientoService implements IMovimientoService {
    private final IMovimientoRepository movimientoRepository;

    private final ICuentaRepository cuentaRepository;

    public MovimientoService(IMovimientoRepository movimientoRepository, ICuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public List<Movimiento> getMovimientos() {
        List<MovimientoEntity> movimientos = movimientoRepository.findAllWithCuenta();
        return movimientos.stream().map(MovimientoService::mapEntityToDomain).toList();
    }

    @Override
    public Movimiento findByIdMovimiento(long idMovimiento) {
        MovimientoEntity movimientoEntity = movimientoRepository.findByIdWithCuenta(idMovimiento).orElse(null);
        if (movimientoEntity == null) {
            return null;
        }
        return mapEntityToDomain(movimientoEntity);
    }

    private static Movimiento mapEntityToDomain(MovimientoEntity movimientoEntity) {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(movimientoEntity.getId());
        movimiento.setFechaMovimiento(movimientoEntity.getFechaMovimiento());
        movimiento.setSaldo(movimientoEntity.getSaldo());
        movimiento.setMonto(movimientoEntity.getMonto());
        movimiento.setTipoMovimiento(TipoMovimientoEnum.valueOf(movimientoEntity.getTipoMovimiento()));
        movimiento.setNumeroCuenta(movimientoEntity.getCuenta().getNumeroCuenta());
        return movimiento;
    }

    @Override
    @Transactional
    public Movimiento createMovimiento(Movimiento movimiento) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(movimiento.getNumeroCuenta()).orElseThrow(
                () -> new AccountInsufficeFundsException("Cuenta no encontrada")
        );

        MovimientoEntity movimientoEntity = new MovimientoEntity();
        movimientoEntity.setMonto(movimiento.getMonto());
        movimientoEntity.setCuenta(cuentaEntity);
        movimientoEntity.setSaldo(cuentaEntity.getSaldo());
        movimiento.setSaldo(cuentaEntity.getSaldo());
        if (movimiento.getMonto() > 0) {
            movimientoEntity.setTipoMovimiento(TipoMovimientoEnum.DEPOSITO.toString());
            movimiento.setTipoMovimiento(TipoMovimientoEnum.DEPOSITO);
        } else {
            movimientoEntity.setTipoMovimiento(TipoMovimientoEnum.RETIRO.toString());
            movimiento.setTipoMovimiento(TipoMovimientoEnum.RETIRO);
        }
        MovimientoEntity movimientoSaved = movimientoRepository.save(movimientoEntity);

        movimiento.setId(movimientoSaved.getId());
        movimiento.setFechaMovimiento(movimientoSaved.getFechaMovimiento());
        if ((cuentaEntity.getSaldo() + movimiento.getMonto())<0) {
            throw new AccountInsufficeFundsException("Saldo insuficiente para realizar esta operación");
        }else{
            cuentaEntity.setSaldo(cuentaEntity.getSaldo() + movimiento.getMonto());
            cuentaRepository.save(cuentaEntity);
        }
        return movimiento;
    }

    @Override
    public Movimiento updateMovimiento(long idMovimiento, Movimiento movimiento) {
        MovimientoEntity movimientoEntity = movimientoRepository.findById(idMovimiento).orElseThrow(
                () -> new ResourceNotFoundException("Movimiento no encontrado")
        );
        movimientoEntity.setMonto(movimiento.getMonto());
        movimientoEntity.setTipoMovimiento(movimiento.getTipoMovimiento().toString());
        MovimientoEntity movimientoSaved = movimientoRepository.save(movimientoEntity);

        movimiento.setId(movimientoSaved.getId());
        movimiento.setFechaMovimiento(movimientoSaved.getFechaMovimiento());
        movimiento.setTipoMovimiento(TipoMovimientoEnum.valueOf(movimientoSaved.getTipoMovimiento()));
        return movimiento;
    }

    @Override
    public boolean existMovimientoById(long idMovimiento) {
        return movimientoRepository.existsById(idMovimiento);
    }

    @Override
    public void deleteMovimiento(long idMovimiento) {
        movimientoRepository.deleteById(idMovimiento);
    }

    @Override
    public List<Movimiento> getMovimientosByCuentaAndDate(long numeroCuenta, Date fechaInicio, Date fechaFin) {
        List<MovimientoEntity> movimientos = movimientoRepository.findByCuentaNumeroCuentaAndFechaMovimientoBetween(numeroCuenta, fechaInicio, fechaFin);
        return movimientos.stream().map(MovimientoService::mapEntityToDomain).toList();
    }
}
