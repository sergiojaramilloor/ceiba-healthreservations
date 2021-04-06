package com.ceiba.reserva.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.reserva.comando.ComandoReserva;
import com.ceiba.reserva.comando.manejador.ManejadorActualizarReserva;
import com.ceiba.reserva.comando.manejador.ManejadorCrearReserva;
import com.ceiba.reserva.comando.manejador.ManejadorEliminarReserva;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/reserva")
@Api(tags = {"Controlador comando reserva"})
public class ComandoControladorReserva {

    private final ManejadorEliminarReserva manejadorEliminarReserva;
    private final ManejadorActualizarReserva manejadorActualizarReserva;
    private final ManejadorCrearReserva manejadorCrearReserva;

    @Autowired
    public ComandoControladorReserva(ManejadorEliminarReserva manejadorEliminarReserva,
                                     ManejadorActualizarReserva manejadorActualizarReserva,
                                     ManejadorCrearReserva manejadorCrearReserva){
        this.manejadorEliminarReserva = manejadorEliminarReserva;
        this.manejadorActualizarReserva = manejadorActualizarReserva;
        this.manejadorCrearReserva = manejadorCrearReserva;
    }

    @PostMapping
    @ApiOperation("crear reserva")
    public ComandoRespuesta<Long> crear(@RequestBody ComandoReserva comandoReserva){
        return manejadorCrearReserva.ejecutar(comandoReserva);
    }

    @DeleteMapping(value="/{idReserva}")
    @ApiOperation("eliminar reserva")
    public void eliminar(@PathVariable Long idReserva){
        manejadorEliminarReserva.ejecutar(idReserva);
    }

    @PutMapping(value="/{idReserva}")
    @ApiOperation("actualizar reserva")
    public void actualizar(@RequestBody ComandoReserva comandoReserva, @PathVariable Long idReserva){
        comandoReserva.setIdReserva(idReserva);
        manejadorActualizarReserva.ejecutar(comandoReserva);
    }

}