package com.ceiba.reserva.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.*;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;
import com.ceiba.reserva.servicio.testdatabuilder.ReservaTestDataBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ServicioCrearReservaTest {

    private static final double PORCENTAJE_A_PAGAR_ESTRATO_TRES_MAYOR_DE_SESENTA_ANOS = 0.92;

    @Test
    public void validaReservaPrevia() {
        //arrange
        Reserva reserva = new ReservaTestDataBuilder().
                                porFechaReserva(
                                        LocalDateTime.now().
                                                plusDays(1)).
                                                build();
        RepositorioReserva repoReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repoReserva.existe(Mockito.anyString())).thenReturn(true);
        ServicioCrearReserva crearReservaService = new ServicioCrearReserva(repoReserva);
        //act
        BasePrueba.assertThrows(() -> crearReservaService.ejecutar(reserva),
                ExcepcionDuplicidad.class,
                "El usuario ya está en el sistema");

    }

    @Test
    public void reservaCreada(){
        Reserva reserva = new ReservaTestDataBuilder().
                                porFechaReserva(
                                        LocalDateTime.now().
                                                plusDays(1)).
                                        build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existe("Sergio Jaramillo Orozco")).thenReturn(true);
        ServicioCrearReserva servicioCrearReserva = new ServicioCrearReserva(repositorioReserva);
        Long registroHecho = servicioCrearReserva.ejecutar(reserva);

        Assert.assertNotEquals(java.util.Optional.ofNullable(registroHecho), 1L);
    }

    @Test
    public void errorReservaCitasPasadas(){
        //arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().
                                                                porFechaReserva(
                                                                        LocalDateTime.of(
                                                                                LocalDateTime.now().getYear(),
                                                                                LocalDateTime.now().getMonth().minus(1),
                                                                                LocalDateTime.now().getDayOfMonth(),
                                                                                LocalDateTime.now().getHour(),
                                                                                LocalDateTime.now().getMinute(),
                                                                                LocalDateTime.now().getSecond()));
        // act - assert
        BasePrueba.assertThrows(()-> reservaTestDataBuilder.build(),
                ExceptionValidarReservaConFechasPasadas.class,
                "Su fecha de reserva tiene una fecha errada, verifiquela por favor");

    }

    @Test
    public void errorReservaNoPermitidaFinDeSemana(){
        //arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().
                                                                porFechaReserva(
                                                                        LocalDateTime.of(
                                                                                LocalDateTime.now().getYear(),
                                                                                LocalDateTime.now().getMonth(),
                                                                                10,//Pendiente por validar como mando un día sábado o domingo
                                                                                LocalDateTime.now().getHour(),
                                                                                LocalDateTime.now().getMinute(),
                                                                                LocalDateTime.now().getSecond()));
        //act
        BasePrueba.assertThrows(()-> reservaTestDataBuilder.build(),
                ExceptionReservaFinDeSemana.class,
                "El sistema no permite realizar reservas el día sábado ni domingo");
    }

    @Test
    public void validarReservaNoPermitidaADiaActual() {
        // arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().
                                                                porFechaReserva(LocalDateTime.now());
        // act - assert
        BasePrueba.assertThrows(()-> reservaTestDataBuilder.build(),
                ExcepcionDiaInvalidoParaSeleccionarCita.class,
                "No puedes reservar citas para el día de hoy por decisión administrativa");

    }

    @Test
    public void validaTarifaDoble(){
        //arrange
        Reserva reservaTestDataBuilder = new ReservaTestDataBuilder().
                                                porFechaReserva(
                                                        LocalDateTime.of(//Pendiente por validar el mandar un día festivo
                                                                2021,
                                                                10,
                                                                18,
                                                                LocalDateTime.now().getHour(),
                                                                LocalDateTime.now().getMinute(),
                                                                LocalDateTime.now().getSecond())).
                                                conIdReservante(400L).
                                                porNombreReservante("Carolina Saldarriaga").
                                                build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        //act
        Mockito.when(repositorioReserva.existeExcluyendoId(Mockito.anyLong(),Mockito.anyString())).thenReturn(true);
        Reserva entidadReserva = reservaTestDataBuilder.validaIncrementoPorReservaDíaFestivo(reservaTestDataBuilder);
        double valorReservaMandadoPorRequest = entidadReserva.getValorReserva();
        double valorEsperadoParaLaReserva = 180000;
        //assert
        Assert.assertEquals(valorEsperadoParaLaReserva,
                valorReservaMandadoPorRequest,
                0.0);
    }

    @Test
    public void validaDescuentoSegunEstratoEdadSuperiorASesentaAnos(){
        //arrange
        Reserva reservaTestDataBuilder = new ReservaTestDataBuilder().
                porFechaReserva(
                    LocalDateTime.of(
                            LocalDateTime.now().getYear(),
                            LocalDateTime.now().getMonth(),
                            27,//Validar cuando este día sea sábado o domingo en el mes corriente
                            LocalDateTime.now().getHour(),
                            LocalDateTime.now().getMinute(),
                            LocalDateTime.now().getSecond())).
                conIdReservante(401L).
                porNombreReservante("Ana Saldarriaga").
                porEstrato(3L).
                porFechaNacimiento(LocalDate.of(1920,10,10)).
                build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        //act
        Mockito.when(repositorioReserva.existeExcluyendoId(Mockito.anyLong(),Mockito.anyString())).thenReturn(true);
        Reserva entidadReserva = reservaTestDataBuilder.validaDescuentoAplicado(reservaTestDataBuilder);
        double valorReservaMandadoPorRequest = entidadReserva.getValorReserva();
        double valorEsperadoParaLaReserva = (90000.00 * PORCENTAJE_A_PAGAR_ESTRATO_TRES_MAYOR_DE_SESENTA_ANOS);
        //assert
        Assert.assertEquals(valorEsperadoParaLaReserva,
                valorReservaMandadoPorRequest,
                0.0);
    }

    @Test
    public void validaPagarPositivoACero(){
        //arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().
                porFechaReserva(LocalDateTime.now().plusDays(1)).
                porNombreReservante("Victor Ruíz").
                porValorReserva(0).
                conIdReservante(1234L);
        //assert
        BasePrueba.assertThrows(()->reservaTestDataBuilder.build(),
                ExcepcionValorInvalido.class,
                "El valor de la cita debe ser positivo");
    }

    @Test
    public void validaEstrato(){
        //arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().
                porFechaReserva(LocalDateTime.now().plusDays(1)).
                porNombreReservante("Maria Builes").
                porEstrato(0L).
                conIdReservante(23456L);
        //assert
        BasePrueba.assertThrows(()->reservaTestDataBuilder.build(),
                ExcepcionValorInvalido.class,
                "El estrato de la persona debe ser positivo");
    }

}
