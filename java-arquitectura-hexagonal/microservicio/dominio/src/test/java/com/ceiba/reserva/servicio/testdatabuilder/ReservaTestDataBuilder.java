package com.ceiba.reserva.servicio.testdatabuilder;

import com.ceiba.reserva.modelo.entidad.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservaTestDataBuilder {

    private int idReserva;
    private int idReservante;
    private String nombreReservante;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaReserva;
    private double valorReserva;
    private int estrato;

    public ReservaTestDataBuilder(){
        idReservante = 1000;
        nombreReservante = "Sergio Jaramillo";
        fechaNacimiento = LocalDate.of(1995, 01, 11);
        fechaReserva = LocalDateTime.now();
        valorReserva = 90.000;
        estrato = 3;
    }

    public ReservaTestDataBuilder conIdReservante(int idReservante){
        this.idReservante = idReservante;
        return this;
    }

    public ReservaTestDataBuilder porIdReserva(int idReserva){
        this.idReserva = idReserva;
        return this;
    }


    public Reserva build(){
        return new Reserva(idReserva, idReservante, nombreReservante, fechaNacimiento, fechaReserva, valorReserva, estrato);
    }

}
