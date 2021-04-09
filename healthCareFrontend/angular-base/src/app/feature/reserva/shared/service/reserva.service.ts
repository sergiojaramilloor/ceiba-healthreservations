import { Injectable } from '@angular/core';
import { HttpService } from '@core-service/http.service';
import { Reserva } from '../model/reserva';
import { Observable } from 'rxjs';


@Injectable()
export class ReservaService {

  constructor(protected http: HttpService) {}

  public consultar(): Observable<any> {
    return this.http.doGet<Reserva[]>(`/reservas`);
  }

  public guardar(reserva: Reserva): Observable<any> {
    this.validarFechaNacimiento(reserva);
    return this.http.doPost<Reserva, boolean>(`/reserva`, reserva)
  }

  public eliminar(reserva: Reserva): Observable<any> {
    return this.http.doDelete<boolean>(`/reserva/${reserva.idReserva}`);
  }

  public consultarUno(idReserva: number): Observable<any> {
    return this.http.doGet<Reserva>(`/reservas/${idReserva}`);
  }

  public actualizar(reserva: Reserva): Observable<any> {
    this.validarFechaNacimiento(reserva);
    return this.http.doPut<Reserva>(`/reserva/${reserva.idReserva}`, reserva);

  }

  validarFechaNacimiento(reserva: Reserva){
    let fechaNacimiento = reserva.fechaNacimiento;
    let newDate = new Date(fechaNacimiento);
    let actualDate = new Date;
    if(newDate > actualDate){
      alert("La fecha de nacimiento que estás mandando es inválida");
    }
  }
}
