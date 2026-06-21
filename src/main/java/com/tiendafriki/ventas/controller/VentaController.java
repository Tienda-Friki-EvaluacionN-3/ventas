package com.tiendafriki.ventas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tiendafriki.ventas.dto.VentaResponseDTO;
import com.tiendafriki.ventas.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/ventas")

public class VentaController {

    private VentaService service;

    public VentaController(
            VentaService service
    ) {

        this.service = service;
    }

    // GET: listar
    @Operation(
        summary = "Listar ventas",
        description = "Obtiene una lista con todas las ventas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio")  ,
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/listar")

    public List<VentaResponseDTO> listar(){

        return service.listar();
    }

    // GET: buscar por id
    @Operation(
        summary = "Buscar venta por id",
        description = "Obtiene una venta por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/buscarId/{id}")

    public VentaResponseDTO buscarPorId(
            @PathVariable Integer id
    ) {

        return service.buscarPorId(id);
    }

    // GET: buscar por fecha
    @Operation(
        summary = "Buscar ventas por fecha",
        description = "Obtiene una lista de ventas realizadas en esa misma fecha"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas obtenidas correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio"),
            @ApiResponse(responseCode = "404", description = "Ventas no encontradas para esa fecha"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en la comunicación entre microservicios")
    })

    @GetMapping("/buscarPorFecha/{fecha}")

    public List<VentaResponseDTO> buscarPorFecha(
            @PathVariable LocalDate fecha
    ){

        return service.buscarPorFecha(fecha);
    }

    // GET: buscar por id de pedido
    @Operation(
        summary = "Buscar venta por id de pedido",
        description = "Obtiene una venta por la id del pedido a la que esta asociada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o Error de logica de negocio"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o en la comunicación entre microservicios")
    })

    @GetMapping("/buscarPorIdPedido/{id}")

    public VentaResponseDTO buscarPorIdPedido(
            @PathVariable Integer id
    ) {

        return service.buscarPorIdPedido(id);
    }

    // POST: Registrar ventas
    @Operation(
        summary = "Sincronizar ventas",
        description = "Permite sincronizar con pedidos pagados para registrar ventas automaticamente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas sincronizadas exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor o de comunicación entre microservicios")
    })

    @PostMapping("/sincronizar")

    public ResponseEntity<?> sincronizar(){

        return ResponseEntity.ok(
                service.sincronizarVentas()
        );
    }

    // GET: total diario
    @Operation(
        summary = "Mostrar total diario de ventas",
        description = "Obtiene el total diario de ventas en el sistema o 0 en caso de que no haya ventas registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta de total diario exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/total-diario")

    public ResponseEntity<?> totalDiario(){

        Double total = service.totalDiario();

        return ResponseEntity.ok(
                "[+] El total diario es: $" + total
        );
    }

    // GET: total diario
    @Operation(
        summary = "Mostrar total mensual de ventas",
        description = "Obtiene el total mensual de ventas en el sistema o 0 en caso de que no haya ventas registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta de total mensual exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @GetMapping("/total-mensual")

    public ResponseEntity<?> totalMensual(){

        Double total = service.totalMensual();

        return ResponseEntity.ok(
                "[+] El total mensual es: $" + total
        );
    }

}