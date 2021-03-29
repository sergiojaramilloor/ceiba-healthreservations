package com.ceiba.dominio.excepcion;

public class ExcepcionFechaDeActualizacionNoPermitida extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExcepcionFechaDeActualizacionNoPermitida(String mensaje){
        super(mensaje);
    }
}
