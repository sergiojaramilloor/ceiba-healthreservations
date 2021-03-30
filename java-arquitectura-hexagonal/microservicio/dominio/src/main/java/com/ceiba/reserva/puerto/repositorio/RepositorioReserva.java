package com.ceiba.reserva.puerto.repositorio;

import com.ceiba.reserva.modelo.entidad.Reserva;

public interface RepositorioReserva {

    Long crearReserva(Reserva reserva);

    void eliminarReserva(Long idReserva);

    void actualizarReserva(Reserva reserva);

    boolean existeExcluyendoId(Long id,String nombre);

    boolean existe(String nombre);

}
