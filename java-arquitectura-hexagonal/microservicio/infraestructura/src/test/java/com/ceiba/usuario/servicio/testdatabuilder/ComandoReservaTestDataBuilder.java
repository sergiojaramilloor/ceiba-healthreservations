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
        fechaReserva = LocalDateTime.now().plusDays(1);
        valorReserva = 100000;
        estrato = 4L;
    }

    public ComandoReservaTestDataBuilder porIdReservante(Long idReservante){
        this.idReservante = idReservante;
        return this;
    }
    public ComandoReservaTestDataBuilder porNombreReservante(String nombreReservante){
        this.nombreReservante = nombreReservante;
        return this;
    }

    public ComandoReservaTestDataBuilder porFechaReserva(LocalDateTime fechaReserva){
        this.fechaReserva = fechaReserva;
        return this;
    }

    public ComandoReserva builder(){
        return new ComandoReserva(idReserva, idReservante, nombreReservante, fechaNacimiento, fechaReserva, valorReserva, estrato);
    }
}
