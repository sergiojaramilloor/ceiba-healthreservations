package com.ceiba.dominio.excepcion;

public class ExcepcionDiaInvalidoParaSeleccionarCita extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExcepcionDiaInvalidoParaSeleccionarCita(String mensaje){
        super(mensaje);
    }

}
