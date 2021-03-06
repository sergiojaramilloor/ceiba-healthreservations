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
        ComandoReserva reserva = new ComandoReservaTestDataBuilder().
                                        porNombreReservante("Antonia Giraldo").
                                        builder();
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
        ComandoReserva reserva = new ComandoReservaTestDataBuilder().builder();
        // act - assert
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

    @Test
    public void obtenerReserva() throws Exception{
        //arrange
        Long idReserva = 1L;
        //act
        mockMvc.perform(get("/reservas/{idReserva}", idReserva)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
