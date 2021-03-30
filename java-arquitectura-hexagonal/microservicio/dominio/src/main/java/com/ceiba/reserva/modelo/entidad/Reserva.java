package com.ceiba.reserva.modelo.entidad;

import com.ceiba.dominio.excepcion.ExcepcionDiaInvalidoParaSeleccionarCita;
import com.ceiba.dominio.excepcion.ExcepcionFechaDeActualizacionNoPermitida;
import com.ceiba.dominio.excepcion.ExceptionReservaFinDeSemana;
import com.ceiba.dominio.excepcion.ExceptionValidarReservaConFechasPasadas;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static com.ceiba.dominio.ValidadorArgumento.validarObligatorio;

@Getter
public class Reserva {

    public static final String SE_DEBE_INGRESAR_LA_FECHA_DE_LA_CITA = "Se debe ingresar la fecha de la cita por agendar";
    public static final String SE_DEBE_AGREGAR_EL_NOMBRE_DEL_RESERVANTE = "Por favor ingrese el nombre de la persona que accederá a la cita";
    public static final String SE_DEBE_INGRESAR_EL_NUMERO_DE_DOCUMENTO = "Por favor ingrese el número de documento de la persona que accederá a la cita";
    public static final String SE_DEBE_INGRESAR_LA_FECHA_DE_NACIMIENTO = "Debes de ingresar la fecha de nacimiento";
    public static final String SE_DEBE_DAR_ESTRATO_DE_LA_PERSONA = "Debes ingresar el estrato de la vivienda donde vives";
    public static final String MENSAJE_ERROR_VIOLACION_LONGITUD_MINIMA_DOCUMENTO = "La longitud del documento ingresado es errónea";
    public static final String DEBE_INGRESAR_VALOR_DE_LA_RESERVA = "Debes proveer el valor de la reserva";    private static final String ERROR_EN_FECHA_DE_ACTUALIZACION = "Error en la fecha ingresada, los días impares no se permiten ajustes en las citas";
    private static final String NO_SE_ADMITEN_RESERVAS_SABADOS_NI_DOMINGOS = "El sistema no permite realizar reservas el día sábado ni domingo";
    private static final String NO_SE_PUEDE_RESERVAR_EL_DIA_ACTUAL = "No puedes reservar citas para el día de hoy por decisión administrativa";
    private static final String FECHA_DE_RESERVA_INVALIDA = "Su fecha de reserva tiene una fecha errada, verifiquela por favor";


    private Long idReserva;
    private Long idReservante;
    private String nombreReservante;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaReserva;
    private double valorReserva;
    private Long estrato;

    public Reserva(Long idReserva, Long idReservante, String nombreReservante, LocalDate fechaNacimiento, LocalDateTime fechaReserva, double valorReserva, Long estrato) {
        validarObligatorio(nombreReservante, SE_DEBE_AGREGAR_EL_NOMBRE_DEL_RESERVANTE);
        validarObligatorio(fechaReserva, SE_DEBE_INGRESAR_LA_FECHA_DE_LA_CITA);
        validarObligatorio(idReservante, SE_DEBE_INGRESAR_EL_NUMERO_DE_DOCUMENTO);
        validarObligatorio(fechaNacimiento, SE_DEBE_INGRESAR_LA_FECHA_DE_NACIMIENTO);
        validarObligatorio(estrato, SE_DEBE_DAR_ESTRATO_DE_LA_PERSONA);
        validarObligatorio(valorReserva, DEBE_INGRESAR_VALOR_DE_LA_RESERVA);
        validarDiaNoImparParaActualizarReserva(fechaReserva);
        validarAgendamiento(fechaReserva);
        validarSiSeTrataDeAgendarCitaSabadoODomingo(fechaReserva);

        this.idReserva = idReserva;
        this.idReservante = idReservante;
        this.nombreReservante = nombreReservante;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaReserva = fechaReserva;
        this.valorReserva = valorReserva;
        this.estrato = estrato;
    }

    private void validarDiaNoImparParaActualizarReserva(LocalDateTime fechaReserva) {
        if(fechaReserva.getDayOfMonth() % 2 != 0){
            throw new ExcepcionFechaDeActualizacionNoPermitida(ERROR_EN_FECHA_DE_ACTUALIZACION);
        }
    }

    private void validarAgendamiento(LocalDateTime fechaReserva) {
        DayOfWeek diaReserva = fechaReserva.getDayOfWeek();
        Date today = Calendar.getInstance().getTime();
        if(diaReserva.equals(LocalDateTime.now().getDayOfWeek())){
            throw new ExcepcionDiaInvalidoParaSeleccionarCita(NO_SE_PUEDE_RESERVAR_EL_DIA_ACTUAL);
        } else if(fechaReserva.isBefore(LocalDateTime.now())){
            throw new ExceptionValidarReservaConFechasPasadas(FECHA_DE_RESERVA_INVALIDA);
        }
    }

    private void validarSiSeTrataDeAgendarCitaSabadoODomingo(LocalDateTime fechaReserva) {
        DayOfWeek diaReserva = fechaReserva.getDayOfWeek();
        if(diaReserva == DayOfWeek.SUNDAY || diaReserva == DayOfWeek.SATURDAY){
            throw new ExceptionReservaFinDeSemana(NO_SE_ADMITEN_RESERVAS_SABADOS_NI_DOMINGOS);
        }
    }
}
