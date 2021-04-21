package com.ceiba.dominio.excepcion;

public class ExcepcionTarifaMaxima extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExcepcionTarifaMaxima(String mensaje){
        super(mensaje);
    }
}
