package com.tiendafriki.ventas.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "Ventas")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "[ERROR] El id de pedido es obligatorio [X_X]")
    @Positive(message = "[ERROR] El id de pedido debe ser mayor a cero [X_X]")
    private Integer pedidoId;

    @NotNull(message = "[ERROR] El total es obligatorio [X_X]")
    @Positive(message = "[ERROR] El total debe ser un numero positivo [X_X]")
    private Double total;

    private LocalDate fecha;

}
