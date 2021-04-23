package com.ceiba.reserva.modelo.entidad;

import com.ceiba.dominio.excepcion.*;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.ceiba.dominio.ValidadorArgumento.*;

@Getter
public class Reserva {

    public static final String SE_DEBE_INGRESAR_LA_FECHA_DE_LA_CITA = "Se debe ingresar la fecha de la cita por agendar";
    public static final String SE_DEBE_AGREGAR_EL_NOMBRE_DEL_RESERVANTE = "Por favor ingrese el nombre de la persona que accederá a la cita";
    public static final String SE_DEBE_INGRESAR_EL_NUMERO_DE_DOCUMENTO = "Por favor ingrese el número de documento de la persona que accederá a la cita";
    public static final String SE_DEBE_INGRESAR_LA_FECHA_DE_NACIMIENTO = "Debes de ingresar la fecha de nacimiento";
    public static final String SE_DEBE_DAR_ESTRATO_DE_LA_PERSONA = "Debes ingresar el estrato de la vivienda donde vives";
    public static final String MENSAJE_ERROR_VIOLACION_LONGITUD_MINIMA_DOCUMENTO = "La longitud del documento ingresado es errónea";
    public static final String DEBE_INGRESAR_VALOR_DE_LA_RESERVA = "Debes proveer el valor de la reserva";
    private static final String ERROR_EN_FECHA_DE_ACTUALIZACION = "Error en la fecha ingresada, los días impares no se permiten ajustes en las citas";
    public static final String NO_SE_ADMITEN_RESERVAS_SABADOS_NI_DOMINGOS = "El sistema no permite realizar reservas el día sábado ni domingo";
    public static final String NO_SE_PUEDE_RESERVAR_EL_DIA_ACTUAL = "No puedes reservar citas para el día de hoy por decisión administrativa";
    public static final String VALIDAR_VALOR_RESERVA_SEA_POSITIVO = "El valor de la cita debe ser positivo";
    public static final String FECHA_DE_RESERVA_INVALIDA = "Su fecha de reserva tiene una fecha errada, verifiquela por favor";
    public static final String ESTRATO_DE_LA_PERSONA_DEBE_SER_POSITIVO = "El estrato de la persona debe ser positivo";
    public static final String ERROR_EN_EDAD_MINIMA = "La edad mínima para acceder a las citas es de 5 años";
    public static final double PORCENTAJE_PARA_ESTRATO_UNO = 0.85;
    public static final double PORCENTAJE_PARA_ESTRATO_DOS = 0.88;
    public static final double PORCENTAJE_PARA_ESTRATO_TRES = 0.92;
    public static final double PORCENTAJE_PARA_ESTRATOS_DEL_CUATRO_EN_ADELANTE = 0.97;
    public static final int EDAD_MINIMA_PARA_DESCUENTO_PARA_CITA = 60;
    public static final int ESTRATO_UNO = 1;
    public static final int ESTRATO_DOS = 2;
    public static final int ESTRATO_TRES = 3;
    public static final int EDAD_MINIMA = 5;
    public static final int TARIFA_DIAS_FESTIVOS = 2;
    public static final int VALOR_MINIMO_RESERVA = 18000;
    public static final int VALOR_MAXIMO_RESERVA = 110000;

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
        validarPositivo(valorReserva, VALIDAR_VALOR_RESERVA_SEA_POSITIVO);
        validarPositivo(Double.valueOf(estrato), ESTRATO_DE_LA_PERSONA_DEBE_SER_POSITIVO);
        validarEdadMinima(fechaNacimiento);
        validarAgendamiento(fechaReserva);
        validarSiSeTrataDeAgendarCitaSabadoODomingo(fechaReserva);
        validaValorMinimoAPagarPorReserva(valorReserva);
        valorMaximoAPagarPorReserva(valorReserva);

        this.idReserva = idReserva;
        this.idReservante = idReservante;
        this.nombreReservante = nombreReservante;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaReserva = fechaReserva;
        this.valorReserva = valorReserva;
        this.estrato = estrato;
    }

    public void validarDiaNoImparParaActualizarReserva(LocalDateTime fechaReserva) {
        if(fechaReserva.getDayOfMonth() % 2 != 0){
            throw new ExcepcionFechaDeActualizacionNoPermitida(ERROR_EN_FECHA_DE_ACTUALIZACION);
        }
    }

    private void validarAgendamiento(LocalDateTime fechaReserva) {
        int diaReserva = fechaReserva.getDayOfMonth();
        Month mesReserva = fechaReserva.getMonth();
        if(diaReserva == LocalDateTime.now().getDayOfMonth() &&
                mesReserva.equals(LocalDateTime.now().getMonth())){
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

    public Reserva calculaDescuentoPorAplicar(Reserva reserva){
       return calcularDescuento(reserva);
    }
    private Reserva calcularDescuento(Reserva reserva) {
        int anoNacimiento = reserva.getFechaNacimiento().getYear();
        int anoActual = LocalDate.now().getYear();
        if((anoActual - anoNacimiento) >= EDAD_MINIMA_PARA_DESCUENTO_PARA_CITA ){
            Long estratoUsuario = reserva.getEstrato();
            valorReserva = (reserva.getValorReserva()) * calculaDescuentoAPagoPorEdadYEstrato(estratoUsuario);
        }
        validaElIncrementoPorReservaDiaFestivo(reserva);
        return reserva;
    }

    private Double calculaDescuentoAPagoPorEdadYEstrato(Long estratoUsuario) {
        Double valorPorPagar = PORCENTAJE_PARA_ESTRATOS_DEL_CUATRO_EN_ADELANTE;
        if(estratoUsuario == ESTRATO_UNO){
            valorPorPagar = PORCENTAJE_PARA_ESTRATO_UNO;
        } else if (estratoUsuario == ESTRATO_DOS){
            valorPorPagar = PORCENTAJE_PARA_ESTRATO_DOS;
        } else if (estratoUsuario == ESTRATO_TRES){
            valorPorPagar = PORCENTAJE_PARA_ESTRATO_TRES;
        }
        return valorPorPagar;
    }

    private Reserva validaElIncrementoPorReservaDiaFestivo(Reserva reserva){
        List<LocalDate> retornaDiasFestivos = listaDiasFestivo();
        if(retornaDiasFestivos.contains(reserva.getFechaReserva().toLocalDate())){
            valorReserva = reserva.getValorReserva() * TARIFA_DIAS_FESTIVOS;
        }
        return this;
    }

    private List<LocalDate> listaDiasFestivo(){
        List<LocalDate> diasFestivos = new ArrayList<>();
        diasFestivos.add(LocalDate.of(2021, 5, 1));
        diasFestivos.add(LocalDate.of(2021, 5, 17));
        diasFestivos.add(LocalDate.of(2021, 6, 7));
        diasFestivos.add(LocalDate.of(2021, 6, 14));
        diasFestivos.add(LocalDate.of(2021, 7, 5));
        diasFestivos.add(LocalDate.of(2021, 7, 20));
        diasFestivos.add(LocalDate.of(2021, 8, 7));
        diasFestivos.add(LocalDate.of(2021, 8, 16));
        diasFestivos.add(LocalDate.of(2021, 10, 18));
        diasFestivos.add(LocalDate.of(2021, 11, 1));
        diasFestivos.add(LocalDate.of(2021, 11, 15));
        diasFestivos.add(LocalDate.of(2021, 12, 8));
        diasFestivos.add(LocalDate.of(2021, 12, 25));

        return diasFestivos;
    }

    private void validarEdadMinima(LocalDate fechaNacimiento){
        if((LocalDate.now().getYear() - fechaNacimiento.getYear()) <= EDAD_MINIMA){
            throw new ExcepcionEdadMinimaIncumplida(ERROR_EN_EDAD_MINIMA);
        }
    }

    private void validaValorMinimoAPagarPorReserva(double valorReserva){
        if(valorReserva < VALOR_MINIMO_RESERVA){
            throw new ExcepcionTarifaMinima("Señor usuario, el monto a pagar por la reserva, mínimo es de 18.000");
        }
    }

    private void valorMaximoAPagarPorReserva(double valorReserva){
        if(valorReserva > VALOR_MAXIMO_RESERVA){
            throw new ExcepcionTarifaMaxima("Señor usuario, el monto a pagar por la reserva, máximo es de 110.000");
        }
    }
}
