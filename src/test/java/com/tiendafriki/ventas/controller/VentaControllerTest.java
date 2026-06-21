package com.tiendafriki.ventas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tiendafriki.ventas.controller.VentaController;
import com.tiendafriki.ventas.dto.VentaResponseDTO;
import com.tiendafriki.ventas.service.VentaService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VentaService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private VentaResponseDTO crearResponseDTO() {

        return new VentaResponseDTO(
                1,
                1,
                15000.0,
                LocalDate.now()
        );
    }

    @Test
    void listarVentas() throws Exception {

        when(service.listar())
                .thenReturn(List.of(crearResponseDTO()));

        mockMvc.perform(get("/ventas/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId() throws Exception {

        when(service.buscarPorId(1))
                .thenReturn(crearResponseDTO());

        mockMvc.perform(get("/ventas/buscarId/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorFecha() throws Exception {

        LocalDate fecha = LocalDate.now();

        when(service.buscarPorFecha(fecha))
                .thenReturn(List.of(crearResponseDTO()));

        mockMvc.perform(
                get("/ventas/buscarPorFecha/" + fecha)
        )
        .andExpect(status().isOk());
    }

    @Test
    void buscarPorIdPedido() throws Exception {

        when(service.buscarPorIdPedido(1))
                .thenReturn(crearResponseDTO());

        mockMvc.perform(get("/ventas/buscarPorIdPedido/1"))
                .andExpect(status().isOk());
    }

    @Test
    void sincronizarVentas() throws Exception {

        when(service.sincronizarVentas())
                .thenReturn("[+] Ventas sincronizadas: 1");

        mockMvc.perform(post("/ventas/sincronizar"))
                .andExpect(status().isOk());
    }

    @Test
    void totalDiario() throws Exception {

        when(service.totalDiario())
                .thenReturn(15000.0);

        mockMvc.perform(get("/ventas/total-diario"))
                .andExpect(status().isOk());
    }

    @Test
    void totalMensual() throws Exception {

        when(service.totalMensual())
                .thenReturn(350000.0);

        mockMvc.perform(get("/ventas/total-mensual"))
                .andExpect(status().isOk());
    }
}