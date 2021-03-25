package com.ceiba.reserva.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionDuplicidad;
import com.ceiba.reserva.modelo.entidad.Reserva;
import com.ceiba.reserva.puerto.repositorio.RepositorioReserva;
import com.ceiba.reserva.servicio.testdatabuilder.ReservaTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

public class ServicioCrearReservaTest {

    @Test
    public void validaReservaPrevia(){
        //arrange
        Reserva reserva = new ReservaTestDataBuilder().build();
        RepositorioReserva repoReserva = Mockito.mock(RepositorioReserva.class);
        Mockito.when(repoReserva.existe(Mockito.anyString())).thenReturn(true);
        ServicioCrearReserva crearReservaService = new ServicioCrearReserva(repoReserva);
        //act
        BasePrueba.assertThrows(() -> crearReservaService.ejecutar(reserva), ExcepcionDuplicidad.class, "El usuario ya está en el sistema");

    }

}
