package com.ceiba.reserva.puerto.repositorio;

import com.ceiba.reserva.modelo.entidad.Reserva;

public interface RepositorioReserva {

    int crearReserva(Reserva reserva);

    void eliminarReserva(int idReservante);

    void actualizarReserva(Reserva reserva);

    boolean existeExcluyendoId(int id,String nombre);

    boolean existe(String nombre);

}
