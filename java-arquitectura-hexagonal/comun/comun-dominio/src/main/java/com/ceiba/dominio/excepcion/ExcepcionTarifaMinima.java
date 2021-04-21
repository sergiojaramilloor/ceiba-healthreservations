package com.ceiba.dominio.excepcion;

public class ExcepcionTarifaMinima extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExcepcionTarifaMinima(String mensaje){
        super(mensaje);
    }
}
