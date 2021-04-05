package com.ceiba.reserva.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionDiaInvalidoParaSeleccionarCita;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExceptionReservaFinDeSemana;
import com.ceiba.dominio.excepcion.ExceptionValidarReservaConFechasPasadas;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;
import com.ceiba.reserva.servicio.testdatabuilder.ReservaTestDataBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

public class ServicioCrearReservaTest {

    @Test
    public void validaReservaPrevia() {
        //arrange
        Reserva reserva = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.now().plusDays(1)).build();
        RepositorioReserva repoReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repoReserva.existe(Mockito.anyString())).thenReturn(true);
        ServicioCrearReserva crearReservaService = new ServicioCrearReserva(repoReserva);
        //act
        BasePrueba.assertThrows(() -> crearReservaService.ejecutar(reserva), ExcepcionDuplicidad.class, "El usuario ya está en el sistema");

    }

    @Test
    public void reservaCreada(){
        Reserva reserva = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.now().plusDays(1)).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existe("Sergio Jaramillo Orozco")).thenReturn(true);
        ServicioCrearReserva servicioCrearReserva = new ServicioCrearReserva(repositorioReserva);
        Long registroHecho = servicioCrearReserva.ejecutar(reserva);

        Assert.assertNotEquals(java.util.Optional.ofNullable(registroHecho), 1L);
    }

    @Test
    public void errorReservaCitasPasadas(){
        //arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.of(2021, 03, 24, 10, 00, 00));
        // act - assert
        BasePrueba.assertThrows(()-> reservaTestDataBuilder.build(), ExceptionValidarReservaConFechasPasadas.class, "Su fecha de reserva tiene una fecha errada, verifiquela por favor");

    }

    @Test
    public void errorReservaNoPermitidaFinDeSemana(){
        //arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.of(2021, 04,10,10,00,00));
        //act
        BasePrueba.assertThrows(()-> reservaTestDataBuilder.build(), ExceptionReservaFinDeSemana.class, "El sistema no permite realizar reservas el día sábado ni domingo");
    }

    @Test
    public void validarReservaNoPermitidaADiaActual() {
        // arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.now());
        // act - assert
        BasePrueba.assertThrows(()-> reservaTestDataBuilder.build(), ExcepcionDiaInvalidoParaSeleccionarCita.class, "No puedes reservar citas para el día de hoy por decisión administrativa");

    }

    @Test
    public void validaTarifaDoble(){
        //arrange
        Reserva reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.of(2021, 10, 18, 00, 00, 00)).conIdReservante(400L).porNombreReservante("Carolina Saldarriaga").build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        //act
        Mockito.when(repositorioReserva.existeExcluyendoId(Mockito.anyLong(),Mockito.anyString())).thenReturn(true);
        Reserva entidadReserva = reservaTestDataBuilder.validaIncrementoPorReservaDíaFestivo(reservaTestDataBuilder);
        double valorReservaMandadoPorRequest = entidadReserva.getValorReserva();
        double valorEsperadoParaLaReserva = 180000;
        //assert
        Assert.assertEquals(valorEsperadoParaLaReserva, valorReservaMandadoPorRequest, 0.0);
    }
}
