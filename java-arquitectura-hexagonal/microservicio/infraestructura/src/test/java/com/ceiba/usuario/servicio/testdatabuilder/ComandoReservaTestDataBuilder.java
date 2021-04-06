package com.ceiba.usuario.servicio.testdatabuilder;

import com.ceiba.reserva.comando.ComandoReserva;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ComandoReservaTestDataBuilder {

    private Long idReserva;
    private Long idReservante;
    private String nombreReservante;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaReserva;
    private double valorReserva;
    private Long estrato;

    public ComandoReservaTestDataBuilder(){
        idReserva = 3L;
        idReservante = 3L;
        nombreReservante = "Sergio A. Jaramillo";
        fechaNacimiento = LocalDate.of(1995, 01, 11);
        fechaReserva = LocalDateTime.now();
        valorReserva = 110000.00;
        estrato = 4L;
    }

    public ComandoReserva builder(){
        return new ComandoReserva(idReserva, idReservante, nombreReservante, fechaNacimiento, fechaReserva, valorReserva, estrato);
    }
}