package com.ceiba.dominio.excepcion;

public class ExcepcionLongitudDocumento extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExcepcionLongitudDocumento(String mensaje){
        super(mensaje);
    }
}
