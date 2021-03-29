package com.ceiba.reserva.servicio;

import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;

public class ServicioEliminarReserva {

    private final RepositorioReserva repositorioReserva;

    public ServicioEliminarReserva(RepositorioReserva repositorioReserva){
        this.repositorioReserva = repositorioReserva;
    }

    public void eliminar(Long idReservante){
        this.repositorioReserva.eliminarReserva(idReservante);
    }

}
