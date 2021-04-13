import { ToastrService } from 'ngx-toastr';
import { Injectable } from "@angular/core";

@Injectable()
export class ServicioDeNotificaciones{

    constructor(private toastr: ToastrService){  }

    mostrarExito(mensaje: string): void {
         this.toastr.success('Work done!', mensaje);
     }

     mostrarError(mensaje: string): void {
        this.toastr.error('We have an error in the request!', mensaje);
     }

     mostrarAdvertencia(mensaje: string): void {
        alert(mensaje);
     }

}