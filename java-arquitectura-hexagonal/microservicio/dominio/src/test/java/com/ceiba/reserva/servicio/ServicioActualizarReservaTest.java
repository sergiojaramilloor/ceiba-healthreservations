package com.ceiba.reserva.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionDiaInvalidoParaSeleccionarCita;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionFechaDeActualizacionNoPermitida;
import com.ceiba.dominio.excepcion.ExceptionValidarReservaConFechasPasadas;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;
import com.ceiba.reserva.servicio.testdatabuilder.ReservaTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

public class ServicioActualizarReservaTest {

    @Test
    public void errorFechaReserva(){
        //arrange
        Reserva reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.of(2021, 04, 27, 10, 00, 00)).porNombreReservante("Daniel Jiménez").conIdReservante(200L).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existeExcluyendoId(reservaTestDataBuilder.getIdReservante(), reservaTestDataBuilder.getNombreReservante())).thenReturn(true);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act - assert
        BasePrueba.assertThrows(()-> servicioActualizarReserva.ejecutar(reservaTestDataBuilder), ExcepcionFechaDeActualizacionNoPermitida.class, "Error en la fecha ingresada, los días impares no se permiten ajustes en las citas");

    }

    @Test
    public void errorValorMinimoPorReserva(){
        Reserva reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.of(2021, 04, 26, 10, 00, 00)).porValorReserva(10000).build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existeExcluyendoId(reservaTestDataBuilder.getIdReservante(), reservaTestDataBuilder.getNombreReservante())).thenReturn(true);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        //act-assert
    }

    @Test
    public void errorFechaPasadaPorReserva(){
        //arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.of(2021, 04, 5, 10, 00, 00));
        // act - assert
        BasePrueba.assertThrows(()-> reservaTestDataBuilder.build(), ExceptionValidarReservaConFechasPasadas.class, "Su fecha de reserva tiene una fecha errada, verifiquela por favor");

    }

    @Test
    public void errorFechaDeHoyPorReserva(){
        //arrange
        ReservaTestDataBuilder reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.now());
        // act - assert
        BasePrueba.assertThrows(()-> reservaTestDataBuilder.build(), ExcepcionDiaInvalidoParaSeleccionarCita.class, "No puedes reservar citas para el día de hoy por decisión administrativa");

    }

    @Test
    public void errorUsuarioNoExiste(){
        //arrange
        Reserva reservaTestDataBuilder = new ReservaTestDataBuilder().porFechaReserva(LocalDateTime.of(2021, 04, 8, 10, 00, 00)).porNombreReservante("Daniel Jiménez").build();
        RepositorioReserva repositorioReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repositorioReserva.existeExcluyendoId(reservaTestDataBuilder.getIdReservante(), "Usuario desconocido")).thenReturn(true);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repositorioReserva);
        // act - assert
        BasePrueba.assertThrows(()-> servicioActualizarReserva.ejecutar(reservaTestDataBuilder), ExcepcionDuplicidad.class, "El usuario a actualizar no está en el sistema");

    }

}
