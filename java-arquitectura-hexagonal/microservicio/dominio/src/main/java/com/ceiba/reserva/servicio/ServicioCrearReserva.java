package com.ceiba.reserva.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;

public class ServicioCrearReserva {

    private static final String EL_USUARIO_YA_EXISTE_EN_EL_SISTEMA = "El usuario ya está en el sistema";

    private final RepositorioReserva repositorioReserva;

    public ServicioCrearReserva(RepositorioReserva repositorioReserva){
        this.repositorioReserva = repositorioReserva;
    }

    public Long ejecutar(Reserva reserva){
        validarExistencia(reserva);
        reserva.validaDescuentoAplicado(reserva);
        reserva.validaIncrementoPorReservaDíaFestivo(reserva);
        return this.repositorioReserva.crearReserva(reserva);
    }

    private void validarExistencia(Reserva reserva){
        boolean existe = this.repositorioReserva.existe(reserva.getNombreReservante());
        if(existe){
            throw new ExcepcionDuplicidad(EL_USUARIO_YA_EXISTE_EN_EL_SISTEMA);
        }
    }
}
