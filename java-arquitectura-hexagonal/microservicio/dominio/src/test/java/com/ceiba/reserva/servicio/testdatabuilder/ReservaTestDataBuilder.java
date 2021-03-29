package com.ceiba.reserva.servicio.testdatabuilder;

import com.ceiba.reserva.modelo.entidad.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservaTestDataBuilder {

    private Long idReserva;
    private Long idReservante;
    private String nombreReservante;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaReserva;
    private double valorReserva;
    private Long estrato;

    public ReservaTestDataBuilder(){
        idReservante = 10000005L;
        nombreReservante = "Sergio Jaramillo";
        fechaNacimiento = LocalDate.of(1995, 01, 11);
        fechaReserva = LocalDateTime.now();
        valorReserva = 90.000;
        estrato = 3L;
    }

    public ReservaTestDataBuilder conIdReservante(Long idReservante){
        this.idReservante = idReservante;
        return this;
    }

    public ReservaTestDataBuilder porIdReserva(Long idReserva){
        this.idReserva = idReserva;
        return this;
    }


    public Reserva build() {
        return new Reserva(idReserva, idReservante, nombreReservante, fechaNacimiento, fechaReserva, valorReserva, estrato);
    }

}
