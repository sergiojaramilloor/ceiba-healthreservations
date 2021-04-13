import { ServicioDeNotificaciones } from './../../../../core/services/ServicioDeNotificaciones.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ReservaService } from './../../shared/service/reserva.service';
import { Reserva } from './../../shared/model/reserva';
import { Component, OnInit } from '@angular/core';

const LONGITUD_MINIMA_DOCUMENTO_RESERVANTE = 7;
const LONGITUD_MAXIMA_DOCUMENTO_RESERVANTE = 12;
const LONGITUD_MINIMA_NOMBRE_RESERVATE = 8;
const LONGITUD_MAXIMA_NOMBRE_RESERVANTE = 60;
const VALOR_MINIMO_RESERVA_CITA = 10000;
const VALOR_MAXIMO_RESERVA_CITA = 110000;
const VALOR_MINIMO_ESTRATO = 1;
const VALOR_MAXIMO_ESTRATO = 7;

@Component({
  selector: 'app-crear-reserva',
  templateUrl: './crear-reserva.component.html',
  styleUrls: ['./crear-reserva.component.css']
})
export class CrearReservaComponent implements OnInit {
  reservaForm: FormGroup;
  reserva:Reserva = new Reserva();

  constructor(protected reservaService: ReservaService, private routes: Router, private servicioDeAlertas: ServicioDeNotificaciones) { }

  ngOnInit(): void {
    this.validacionesFormularioReserva();
  }

  guardar(reserva : Reserva){
      reserva.fechaReserva = reserva.fechaReserva.replace("T", " ");
      reserva.fechaReserva = reserva.fechaReserva.concat(":00");
      this.reservaService.guardar(reserva).subscribe(data => {
        this.servicioDeAlertas.mostrarExito('Éxito');
        console.log(data);
        this.routes.navigate(['listar']);
      },
      error => {
        this.servicioDeAlertas.mostrarError(error.error.mensaje);
      }
      );
  }

  private validacionesFormularioReserva(){
    this.reservaForm = new FormGroup({
      idReservante : new FormControl('', [Validators.required, 
                                          Validators.minLength(LONGITUD_MINIMA_DOCUMENTO_RESERVANTE), 
                                          Validators.maxLength(LONGITUD_MAXIMA_DOCUMENTO_RESERVANTE)]),
      nombreReservante : new FormControl('', [Validators.required, 
                                          Validators.minLength(LONGITUD_MINIMA_NOMBRE_RESERVATE),
                                          Validators.maxLength(LONGITUD_MAXIMA_NOMBRE_RESERVANTE)]),
      valorReserva : new FormControl('', [Validators.required, 
                                          Validators.min(VALOR_MINIMO_RESERVA_CITA),
                                          Validators.max(VALOR_MAXIMO_RESERVA_CITA)]),
      estrato : new FormControl('', [Validators.required, 
                                    Validators.min(VALOR_MINIMO_ESTRATO),
                                    Validators.max(VALOR_MAXIMO_ESTRATO)]),
      fechaNacimiento: new FormControl('', [Validators.required]),
      fechaReserva : new FormControl('', [Validators.required])
    });
  }

}
