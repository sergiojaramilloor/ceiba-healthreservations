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
        idReservante = 10005L;
        nombreReservante = "Sergio Jaramillo";
        fechaNacimiento = LocalDate.of(1995, 01, 11);
        fechaReserva = LocalDateTime.of(2021, 04, 2, 10,20,00);
        valorReserva = 90000.00;
        estrato = 3L;
    }

    public ReservaTestDataBuilder porEstrato(Long estrato){
        this.estrato = estrato;
        return this;
    }

    public ReservaTestDataBuilder conIdReservante(Long idReservante){
        this.idReservante = idReservante;
        return this;
    }

    public ReservaTestDataBuilder porNombreReservante(String nombreReservante){
        this.nombreReservante = nombreReservante;
        return this;
    }

    public ReservaTestDataBuilder porValorReserva(double valorReserva){
        this.valorReserva = valorReserva;
        return this;
    }

    public ReservaTestDataBuilder porFechaReserva(LocalDateTime fechaReserva){
        this.fechaReserva = fechaReserva;
        return this;
    }

    public ReservaTestDataBuilder porFechaNacimiento(LocalDate fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public Reserva build() {
        return new Reserva(idReserva, idReservante, nombreReservante, fechaNacimiento, fechaReserva, valorReserva, estrato);
    }

}
