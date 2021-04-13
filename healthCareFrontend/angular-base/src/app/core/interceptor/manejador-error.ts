import { ServicioDeNotificaciones } from './../services/ServicioDeNotificaciones.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorHandler, Injectable, Injector } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HTTP_ERRORES_CODIGO } from './http-codigo-error';

const constatesRespuestaErrorServer = {
  S500: 500,
  S400: 400
}

@Injectable()
export class ManejadorError implements ErrorHandler {

  constructor(private injector: Injector) {}

  handleError(error: any | Error): void {
    const mensajeError = this.mensajePorDefecto(error);
    this.imprimirErrorConsola(mensajeError);
    
    switch(error.status){
      case (constatesRespuestaErrorServer.S400): {
        this.mostrarMensajeError(error.error.mensaje);
        break;
      }
    }
  }

  private mensajePorDefecto(error) {
    if (error instanceof HttpErrorResponse) {
      if (!navigator.onLine) {
        return HTTP_ERRORES_CODIGO.NO_HAY_INTERNET;
      }
      if (error.hasOwnProperty('status') && !error.error.hasOwnProperty('mensaje')) {
        return this.obtenerErrorHttpCode(error.status);
      }    
    }
    return error;
  }

  private imprimirErrorConsola(mensaje): void {
    const respuesta = {
      fecha: new Date().toLocaleString(),
      path: window.location.href,
      mensaje,
    };
    if (!environment.production) {
      window.console.error('Error inesperado:\n', respuesta);
    }
  }

  public obtenerErrorHttpCode(httpCode: number): string {
    if (HTTP_ERRORES_CODIGO.hasOwnProperty(httpCode)) {
      return HTTP_ERRORES_CODIGO.PETICION_FALLIDA;
    }
    return HTTP_ERRORES_CODIGO[httpCode];
  }

  private mostrarMensajeError(mensaje?: string){
    const servicioNotificaciones = this.injector.get(ServicioDeNotificaciones);
    servicioNotificaciones.mostrarError(`${mensaje}`);
    
  }
}
