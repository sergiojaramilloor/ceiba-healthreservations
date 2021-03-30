package com.ceiba.reserva.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;

public class ServicioActualizarReserva {

    private static final String EL_USUARIO_YA_EXISTE_EN_EL_SISTEMA = "El usuario ya existe en el sistema";
    //private static final String ERROR_EN_FECHA_DE_ACTUALIZACION = "Error en la fecha ingresada, los días impares no se permiten ajustes en las citas";

    private final RepositorioReserva repositorioReserva;

    public ServicioActualizarReserva(RepositorioReserva repositorioReserva){
        this.repositorioReserva = repositorioReserva;
    }

    public void ejecutar(Reserva reserva){
        validarExistencia(reserva);
        //if(reserva.getFechaReserva().getDayOfMonth() % 2 != 0){
          //  throw new ExcepcionFechaDeActualizacionNoPermitida(ERROR_EN_FECHA_DE_ACTUALIZACION);
        //} else {
            this.repositorioReserva.actualizarReserva(reserva);
        //}
    }

    private void validarExistencia(Reserva reserva) {
        boolean existe = this.repositorioReserva.existeExcluyendoId(reserva.getIdReserva(), reserva.getNombreReservante());
        if(existe){
            throw new ExcepcionDuplicidad(EL_USUARIO_YA_EXISTE_EN_EL_SISTEMA);
        }
    }

}
