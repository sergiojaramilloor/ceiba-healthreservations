package com.ceiba.dominio.excepcion;

public class ExceptionValidarReservaConFechasPasadas extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExceptionValidarReservaConFechasPasadas(String mensaje){
        super(mensaje);
    }
}
