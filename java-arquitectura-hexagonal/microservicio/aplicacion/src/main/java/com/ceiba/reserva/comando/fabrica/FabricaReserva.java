package com.ceiba.reserva.comando.fabrica;

import com.ceiba.reserva.comando.ComandoReserva;
import com.ceiba.reserva.modelo.entidad.Reserva;
import org.springframework.stereotype.Component;

@Component
public class FabricaReserva {

    public Reserva crear(ComandoReserva comandoReserva){
        return new Reserva(
                comandoReserva.getIdReserva(),
                comandoReserva.getIdReservante(),
                comandoReserva.getNombreReservante(),
                comandoReserva.getFechaNacimiento(),
                comandoReserva.getFechaReserva(),
                comandoReserva.getValorReserva(),
                comandoReserva.getEstrato()
        );
    }
}
