package com.ceiba.reserva.controlador;

import com.ceiba.reserva.consulta.ManejadorListarReservas;
import com.ceiba.reserva.modelo.dto.DtoReserva;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@Api(tags={"Controlador consulta reserva"})
public class ConsultaControladorReserva{

    private final ManejadorListarReservas manejadorListarReservas;

    public ConsultaControladorReserva(ManejadorListarReservas manejadorListarReservas){
        this.manejadorListarReservas = manejadorListarReservas;
    }

    @GetMapping
    @ApiOperation("Listar reservas")
    public List<DtoReserva> listar(){
        return this.manejadorListarReservas.listar();
    }

    @GetMapping(value = "/{idReserva}")
    @ApiOperation("Una reserva")
    public DtoReserva encontrarPorId(@PathVariable Long idReserva){
        return this.manejadorListarReservas.encontrarPorId(idReserva);
    }
}
