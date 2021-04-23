import { ServicioDeNotificaciones } from './../../../../core/services/ServicioDeNotificaciones.service';
import { Reserva } from './../../shared/model/reserva';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ReservaService } from './../../shared/service/reserva.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-listar-reserva',
  templateUrl: './listar-reserva.component.html',
  styleUrls: ['./listar-reserva.component.css']
})
export class ListarReservaComponent implements OnInit {

  public listaDeReservas: Observable<Reserva[]>;

  constructor(protected reservaService: ReservaService, private router: Router, private servicioNotificaciones: ServicioDeNotificaciones) { }

  ngOnInit() {
    this.listaDeReservas = this.reservaService.consultar();
  }

  eliminar(reserva: Reserva){
    this.reservaService.eliminar(reserva).subscribe(data => {
      console.log(data);
      this.servicioNotificaciones.mostrarExito('Se ha eliminado el usuario con éxito');
      this.router.navigate([""]);
    });
  }

  editar(reserva: Reserva){
    localStorage.setItem("idReserva", reserva.idReserva);
    this.router.navigate(['editar']);
  }

}
