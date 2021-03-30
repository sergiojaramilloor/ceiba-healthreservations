package com.ceiba.reserva.modelo.entidad;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.ceiba.dominio.ValidadorArgumento.validarObligatorio;

@Getter
public class Reserva {

    public static final String SE_DEBE_INGRESAR_LA_FECHA_DE_LA_CITA = "Se debe ingresar la fecha de la cita por agendar";
    public static final String SE_DEBE_AGREGAR_EL_NOMBRE_DEL_RESERVANTE = "Por favor ingrese el nombre de la persona que accederá a la cita";
    public static final String SE_DEBE_INGRESAR_EL_NUMERO_DE_DOCUMENTO = "Por favor ingrese el número de documento de la persona que accederá a la cita";
    public static final String SE_DEBE_INGRESAR_LA_FECHA_DE_NACIMIENTO = "Debes de ingresar la fecha de nacimiento";
    public static final String SE_DEBE_DAR_ESTRATO_DE_LA_PERSONA = "Debes ingresar el estrato de la vivienda donde vives";
    public static final String MENSAJE_ERROR_VIOLACION_LONGITUD_MINIMA_DOCUMENTO = "La longitud del documento ingresado es errónea";
    public static final String DEBE_INGRESAR_VALOR_DE_LA_RESERVA = "Debes proveer el valor de la reserva";

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

        this.idReserva = idReserva;
        this.idReservante = idReservante;
        this.nombreReservante = nombreReservante;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaReserva = fechaReserva;
        this.valorReserva = valorReserva;
        this.estrato = estrato;
    }
}
