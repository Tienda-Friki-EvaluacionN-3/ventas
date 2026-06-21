package com.tiendafriki.ventas.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.tiendafriki.ventas.model.Venta;
import java.util.List;


public interface VentaRepository extends JpaRepository<Venta, Integer>{

    Optional<Venta> findByPedidoId(Integer pedidoId);

    List<Venta> findByFecha(LocalDate fecha);

    @Query("""
        SELECT SUM(v.total)
        FROM Venta v
        WHERE v.fecha = :fecha
    """)
    Double totalDiario(
            @Param("fecha") LocalDate fecha
    );

    @Query("""
        SELECT SUM(v.total)
        FROM Venta v
        WHERE MONTH(v.fecha) = :mes
    """)
    Double totalMensual(
            @Param("mes") Integer mes
    );

}
