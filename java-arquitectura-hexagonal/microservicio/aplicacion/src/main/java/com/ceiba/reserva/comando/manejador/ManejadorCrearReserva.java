package com.ceiba.reserva.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import com.ceiba.reserva.comando.ComandoReserva;
import com.ceiba.reserva.comando.fabrica.FabricaReserva;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.servicio.ServicioCrearReserva;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ManejadorCrearReserva implements ManejadorComandoRespuesta<ComandoReserva, Long> {

    private final FabricaReserva fabricaReserva;
    private final ServicioCrearReserva servicioCrearReserva;

    public ManejadorCrearReserva(FabricaReserva fabricaReserva, ServicioCrearReserva servicioCrearReserva){
        this.fabricaReserva = fabricaReserva;
        this.servicioCrearReserva = servicioCrearReserva;
    }

    public ComandoRespuesta<Long> ejecutar(ComandoReserva comandoReserva) {
        calcularDescuento(comandoReserva);
        Reserva reserva = this.fabricaReserva.crear(comandoReserva);
        return new ComandoRespuesta<>(this.servicioCrearReserva.ejecutar(reserva));
    }

    private void calcularDescuento(ComandoReserva comandoReserva) {
        Double descuento= 0.0;
        int anoNacimiento = comandoReserva.getFechaNacimiento().getYear();
        int anoActual = LocalDate.now().getYear();
        if((anoActual - anoNacimiento) >= 60 ){
            Long estratoUsuario = comandoReserva.getEstrato();
            descuento = aplicarDescuentoAPagoPorEdadYEstrato(estratoUsuario);
        }
        comandoReserva.setValorReserva((comandoReserva.getValorReserva())*descuento);
    }

    private Double aplicarDescuentoAPagoPorEdadYEstrato(Long estratoUsuario) {
        Double valorPorPagar = 0.0;
        if(estratoUsuario==1){
            valorPorPagar = 0.85;
        } else if (estratoUsuario==2){
            valorPorPagar = 0.88;
        } else if (estratoUsuario==3){
            valorPorPagar = 0.92;
        } else {
            valorPorPagar = 0.97;
        }
        return valorPorPagar;
    }
}
