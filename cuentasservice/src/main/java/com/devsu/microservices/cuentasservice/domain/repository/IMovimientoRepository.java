package com.devsu.microservices.cuentasservice.domain.repository;

import com.devsu.microservices.cuentasservice.domain.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IMovimientoRepository extends JpaRepository<MovimientoEntity, Long> {

    @Query("SELECT m FROM MovimientoEntity m JOIN FETCH m.cuenta")
    List<MovimientoEntity> findAllWithCuenta();

    @Query("SELECT m FROM MovimientoEntity m JOIN FETCH m.cuenta WHERE m.id = :idMovimiento")
    Optional<MovimientoEntity> findByIdWithCuenta(long idMovimiento);

    List<MovimientoEntity> findByCuentaNumeroCuentaAndFechaMovimientoBetween(long numeroCuenta, Date fechaInicio, Date fechaFin);
}
