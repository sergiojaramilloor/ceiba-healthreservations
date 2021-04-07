package com.ceiba.usuario.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.reserva.comando.ComandoReserva;
import com.ceiba.reserva.controlador.ComandoControladorReserva;
import com.ceiba.usuario.servicio.testdatabuilder.ComandoReservaTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationMock.class)
@WebMvcTest(ComandoControladorReserva.class)
public class ComandoControladorReservaTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void crearReserva() throws Exception{
        ComandoReserva reserva = new ComandoReservaTestDataBuilder().builder();
        // act - assert
        mockMvc.perform(post("/reserva")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk());
    }

    @Test
    public void actualizarReserva() throws Exception{
        //arrange
        Long id = 3L;
        ComandoReserva reserva = new ComandoReservaTestDataBuilder().
                                        porNombreReservante("Sergio Jaramillo").
                                        porIdReservante(100L).
                                        porFechaReserva(LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().plus(1), 20, 10, 00, 00)).
                                        builder();
        // act - assert
        System.out.println(reserva.getValorReserva());
        mockMvc.perform(put("/reserva/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reserva)))
            .andExpect(status().isOk());
    }

    @Test
    public void eliminarReserva() throws Exception{
        //arrange
        Long id = 1L;
        //act-assert
        mockMvc.perform(delete("/reserva/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
