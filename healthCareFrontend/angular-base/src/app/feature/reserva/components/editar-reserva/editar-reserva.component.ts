import { ReservaService } from './../../shared/service/reserva.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Reserva } from './../../shared/model/reserva';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-editar-reserva',
  templateUrl: './editar-reserva.component.html',
  styleUrls: ['./editar-reserva.component.css']
})
export class EditarReservaComponent implements OnInit {
  reserva: Reserva = new Reserva();
  reservaForm : FormGroup;

  constructor(protected router: Router, protected reservaService: ReservaService) { }

  ngOnInit(): void {
    this.editar();
    this.validacionesFormularioReserva();
  }

  guardar(reserva: Reserva){
    this.reservaService.actualizar(reserva).subscribe(data=>{
      this.reserva =data;
      this.router.navigate(['listar']);
    });
  }

  editar(){
    let idReserva = localStorage.getItem("idReserva");
    this.reservaService.consultarUno(+idReserva).subscribe(data =>{
      this.reserva = data;
    })
  }

  private validacionesFormularioReserva(){
    this.reservaForm = new FormGroup({
      idReserva : new FormControl(' ', [Validators.required]),
      idReservante : new FormControl(' ', [Validators.required]),
      nombreReserva : new FormControl(' ', [Validators.required]),
      fechaNacimiento : new FormControl(' ', [Validators.required]),
      fechaReserva : new FormControl(' ', [Validators.required]),
      valorReserva : new FormControl(' ', [Validators.required]),
      estrato : new FormControl(' ', [Validators.required])
    });
  }

}
