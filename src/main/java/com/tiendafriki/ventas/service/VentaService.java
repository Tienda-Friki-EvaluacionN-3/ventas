package com.tiendafriki.ventas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tiendafriki.ventas.dto.VentaResponseDTO;
import com.tiendafriki.ventas.model.Pedido;
import com.tiendafriki.ventas.model.Venta;
import com.tiendafriki.ventas.repository.VentaRepository;

@Service
public class VentaService implements CommandLineRunner {

    private VentaRepository repository;

    public VentaService(VentaRepository repository) {

        this.repository = repository;
    }

    private VentaResponseDTO convertirADTO(
            Venta venta
    ){

        return new VentaResponseDTO(
                venta.getId(),
                venta.getPedidoId(),
                venta.getTotal(),
                venta.getFecha()
        );
    }

    public List<VentaResponseDTO> listar(){

        return repository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<VentaResponseDTO> buscarPorFecha(LocalDate fecha){

        List<Venta> ventas =
                repository.findByFecha(fecha);

        if(ventas.isEmpty()){

            throw new NoSuchElementException(
                    "[ERROR] No existen ventas en esa fecha [X_X] ..."
            );
        }

        return ventas
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public VentaResponseDTO buscarPorId(Integer id){

        return repository.findById(id)

                .map(this::convertirADTO)

                .orElseThrow(() ->

                        new NoSuchElementException(
                                "[ERROR] Venta no encontrada [X_X] ..."
                        )
                );
    }

    public VentaResponseDTO buscarPorIdPedido(Integer id){

        return repository.findByPedidoId(id)

                .map(this::convertirADTO)

                .orElseThrow(() ->

                        new NoSuchElementException(
                                "[ERROR] Venta del pedido no encontrada [X_X] ..."
                        )
                );
    }

    public String sincronizarVentas(){

        RestTemplate restTemplate =
                new RestTemplate();

        String url =
                "http://localhost:8085/pedidos/listar";

        try{

            Pedido[] pedidos =
                    restTemplate.getForObject(
                            url,
                            Pedido[].class
                    );

            int nuevasVentas = 0;

            if(pedidos == null){

                return "[+] Ventas sincronizadas: 0";
            }

            for(Pedido pedido : pedidos){

                if(pedido.getEstado()
                        .equalsIgnoreCase(
                                "PAGADO"
                        )){

                    Optional<Venta> ventaExistente =
                            repository.findByPedidoId(
                                    pedido.getId()
                            );

                    if(ventaExistente.isEmpty()){

                        Venta venta =
                                new Venta();

                        venta.setPedidoId(
                                pedido.getId()
                        );

                        venta.setTotal(
                                pedido.getTotal()
                        );

                        venta.setFecha(
                                LocalDate.now()
                        );

                        repository.save(venta);

                        nuevasVentas++;
                    }
                }
            }

            return "[+] Ventas sincronizadas: "
                    + nuevasVentas;

        }

        catch(Exception e){

        System.out.println(
                "[AVISO] No fue posible sincronizar ventas. "
                + "El microservicio de Pedidos no está disponible."
                + e.getMessage()
        );

        return "[+] Ventas sincronizadas: 0";
        }
    }

    public Double totalDiario(){

        Double total =
                repository.totalDiario(
                        LocalDate.now()
                );

        return total != null ? total : 0;
    }

    public Double totalMensual(){

        int mesActual =
                LocalDate.now().getMonthValue();

        Double total =
                repository.totalMensual(
                        mesActual
                );

        return total != null ? total : 0;
    }

    @Override
    public void run(String... args)
            throws Exception {

        System.out.println(
                sincronizarVentas()
        );
    }

}