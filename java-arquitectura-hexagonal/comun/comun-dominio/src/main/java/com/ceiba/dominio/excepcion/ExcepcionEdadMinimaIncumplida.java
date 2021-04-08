package com.ceiba.dominio.excepcion;

public class ExcepcionEdadMinimaIncumplida extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExcepcionEdadMinimaIncumplida(String mensaje){
        super(mensaje);
    }
}
