package com.ceiba.reserva.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.dominio.excepcion.ExcepcionFechaDeActualizacionNoPermitida;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;
import com.ceiba.reserva.servicio.testdatabuilder.ReservaTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

public class ServicioActualizarReservaTest {

    @Test
    public void actualizarPorIdReservante() {
        //arrange
        Reserva reservaPorActualizar = new ReservaTestDataBuilder().conIdReservante(1000L).build();
        RepositorioReserva repoReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repoReserva.existeExcluyendoId(Mockito.anyLong(), Mockito.anyString())).thenReturn(true);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repoReserva);
        //act
        BasePrueba.assertThrows(() -> servicioActualizarReserva.ejecutar(reservaPorActualizar), ExcepcionDuplicidad.class, "El usuario ya existe en el sistema");

    }

    @Test
    public void actualizarPorIdReserva() {
        //arrange
        Reserva reservaPorActualizar = new ReservaTestDataBuilder().porIdReserva(1L).build();
        RepositorioReserva repoReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repoReserva.existeExcluyendoId(Mockito.anyLong(), Mockito.anyString())).thenReturn(true);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repoReserva);
        //act
        BasePrueba.assertThrows(() -> servicioActualizarReserva.ejecutar(reservaPorActualizar), ExcepcionDuplicidad.class, "El usuario ya existe en el sistema");
    }

    @Test
    public void errorFechaReserva(){
        //arrange
        Reserva reserva = new ReservaTestDataBuilder().porNombreReservante("D").porFechaReserva(LocalDateTime.of(2021, 03, 31, 10, 00, 00)).build();
        RepositorioReserva repoReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repoReserva.existe(Mockito.anyString())).thenReturn(true);
        ServicioActualizarReserva servicioActualizarReserva = new ServicioActualizarReserva(repoReserva);
        //act
        BasePrueba.assertThrows(()-> servicioActualizarReserva.ejecutar(reserva), ExcepcionFechaDeActualizacionNoPermitida.class, "Error en la fecha ingresada, los días impares no se permiten ajustes en las citas");
    }

}
