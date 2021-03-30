package com.ceiba.reserva.servicio;

import com.ceiba.dominio.excepcion.ExcepcionDiaInvalidoParaSeleccionarCita;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExceptionReservaFinDeSemana;
import com.ceiba.dominio.excepcion.ExceptionValidarReservaConFechasPasadas;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class ServicioCrearReserva {

    private static final String EL_USUARIO_YA_EXISTE_EN_EL_SISTEMA = "El usuario ya está en el sistema";
    private static final String NO_SE_ADMITEN_RESERVAS_SABADOS_NI_DOMINGOS = "El sistema no permite realizar reservas el día sábado ni domingo";
    private static final String NO_SE_PUEDE_RESERVAR_EL_DIA_ACTUAL = "No puedes reservar citas para el día de hoy por decisión administrativa";
    private static final String FECHA_DE_RESERVA_INVALIDA = "Su fecha de reserva tiene una fecha errada, verifiquela por favor";

    private final RepositorioReserva repositorioReserva;

    public ServicioCrearReserva(RepositorioReserva repositorioReserva){
        this.repositorioReserva = repositorioReserva;
    }

    public Long ejecutar(Reserva reserva){
        validarExistencia(reserva);
        validarAgendamiento(reserva);
        validarSiSeTrataDeAgendarCitaSabadoODomingo(reserva);
        return this.repositorioReserva.crearReserva(reserva);
    }

    private void validarAgendamiento(Reserva reserva) {
        DayOfWeek diaReserva = reserva.getFechaReserva().getDayOfWeek();
        Date today = Calendar.getInstance().getTime();
        if(diaReserva.equals(LocalDateTime.now().getDayOfWeek())){
            throw new ExcepcionDiaInvalidoParaSeleccionarCita(NO_SE_PUEDE_RESERVAR_EL_DIA_ACTUAL);
        } else if(reserva.getFechaReserva().isBefore(LocalDateTime.now())){
            throw new ExceptionValidarReservaConFechasPasadas(FECHA_DE_RESERVA_INVALIDA);
        }
    }

    private void validarSiSeTrataDeAgendarCitaSabadoODomingo(Reserva reserva) {
        DayOfWeek diaReserva = reserva.getFechaReserva().getDayOfWeek();
        if(diaReserva == DayOfWeek.SUNDAY || diaReserva == DayOfWeek.SATURDAY){
            throw new ExceptionReservaFinDeSemana(NO_SE_ADMITEN_RESERVAS_SABADOS_NI_DOMINGOS);
        }
    }

    private void validarExistencia(Reserva reserva){
        boolean existe = this.repositorioReserva.existe(reserva.getNombreReservante());
        if(existe){
            throw new ExcepcionDuplicidad(EL_USUARIO_YA_EXISTE_EN_EL_SISTEMA);
        }
    }
}
