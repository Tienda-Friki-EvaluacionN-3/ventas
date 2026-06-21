package com.tiendafriki.ventas.model;

import lombok.Data;

@Data
public class Pedido {

    private Integer id;

    private double total;

    private String estado;

}
