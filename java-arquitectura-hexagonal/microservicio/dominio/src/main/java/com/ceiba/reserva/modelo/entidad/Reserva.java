package com.ceiba.reserva.modelo.entidad;

import com.ceiba.dominio.excepcion.ExcepcionDiaInvalidoParaSeleccionarCita;
import com.ceiba.dominio.excepcion.ExcepcionFechaDeActualizacionNoPermitida;
import com.ceiba.dominio.excepcion.ExceptionReservaFinDeSemana;
import com.ceiba.dominio.excepcion.ExceptionValidarReservaConFechasPasadas;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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
    public static final String NO_SE_ADMITEN_RESERVAS_SABADOS_NI_DOMINGOS = "El sistema no permite realizar reservas el día sábado ni domingo";
    public static final String NO_SE_PUEDE_RESERVAR_EL_DIA_ACTUAL = "No puedes reservar citas para el día de hoy por decisión administrativa";
    public static final String FECHA_DE_RESERVA_INVALIDA = "Su fecha de reserva tiene una fecha errada, verifiquela por favor";
    public static final int EDAD_MINIMA_PARA_DESCUENTO_PARA_CITA = 60;

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

    public Reserva validaDescuentoAplicado(Reserva reserva){
       return calcularDescuento(reserva);
    }
    private Reserva calcularDescuento(Reserva reserva) {
        Double descuento= 0.0;
        int anoNacimiento = reserva.getFechaNacimiento().getYear();
        int anoActual = LocalDate.now().getYear();
        if((anoActual - anoNacimiento) >= EDAD_MINIMA_PARA_DESCUENTO_PARA_CITA ){
            Long estratoUsuario = reserva.getEstrato();
            descuento = aplicarDescuentoAPagoPorEdadYEstrato(estratoUsuario);
            valorReserva = (reserva.getValorReserva())*descuento;
        }
        return reserva;
    }

    private Double aplicarDescuentoAPagoPorEdadYEstrato(Long estratoUsuario) {
        Double valorPorPagar = 0.0;
        if(estratoUsuario==1){
            valorPorPagar = 0.85;
        } else if (estratoUsuario==2){
            valorPorPagar = 0.88;
        } else if (estratoUsuario==3){
            valorPorPagar = 0.92;
        } else {
            valorPorPagar = 0.97;
        }
        return valorPorPagar;
    }

    public Reserva validaIncrementoPorReservaDíaFestivo(Reserva reserva){
        List<LocalDateTime> retornaDiasFestivos = listaDiasFestivo();
        if(retornaDiasFestivos.contains(reserva.getFechaReserva())){
            valorReserva = reserva.getValorReserva()*2;
        }
        return this;
    }

    private List<LocalDateTime> listaDiasFestivo(){
        List<LocalDateTime> diasFestivos = new ArrayList<>();
        diasFestivos.add(LocalDateTime.of(2021, 05, 01, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 05, 17, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 06, 07, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 06, 14, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 07, 05, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 07, 20, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 8, 07, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 8, 16, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 10, 18, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 11, 01, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 11, 15, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 12, 8, 00, 00, 00));
        diasFestivos.add(LocalDateTime.of(2021, 12, 25, 00, 00, 00));

        return diasFestivos;
    }
}
