package com.tiendafriki.ventas.dto;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VentaResponseDTO {

    private Integer id;

    private Integer pedidoId;

    private double total;

    private LocalDate fecha;

}
