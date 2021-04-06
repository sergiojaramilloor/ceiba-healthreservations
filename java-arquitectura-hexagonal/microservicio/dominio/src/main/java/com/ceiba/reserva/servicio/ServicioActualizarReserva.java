package com.ceiba.reserva.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;

public class ServicioActualizarReserva {

    private static final String EL_USUARIO_NO_EXISTE_EN_EL_SISTEMA = "El usuario a actualizar no está en el sistema";

    private final RepositorioReserva repositorioReserva;

    public ServicioActualizarReserva(RepositorioReserva repositorioReserva){
        this.repositorioReserva = repositorioReserva;
    }

    public void ejecutar(Reserva reserva){
        validarExistencia(reserva);
        reserva.validarDiaNoImparParaActualizarReserva(reserva.getFechaReserva());
        this.repositorioReserva.actualizarReserva(reserva);
    }

    private void validarExistencia(Reserva reserva){
        boolean existe = this.repositorioReserva.existeExcluyendoId(reserva.getIdReservante(), reserva.getNombreReservante());
        if(!existe){
            throw new ExcepcionDuplicidad(EL_USUARIO_NO_EXISTE_EN_EL_SISTEMA);
        }
    }
}
