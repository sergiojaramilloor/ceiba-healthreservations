package com.ceiba.dominio.excepcion;

public class ExceptionReservaFinDeSemana extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public ExceptionReservaFinDeSemana(String mensaje){
        super(mensaje);
    }
}
