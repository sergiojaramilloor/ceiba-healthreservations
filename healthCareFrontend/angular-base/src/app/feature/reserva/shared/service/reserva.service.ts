import { Injectable } from '@angular/core';
import { HttpService } from '@core-service/http.service';
import { environment } from 'src/environments/environment';
import { Reserva } from '../model/reserva';


@Injectable()
export class ReservaService {

  constructor(protected http: HttpService) {}

  public consultar() {
    return this.http.doGet<Reserva[]>(`${environment.endpoint}/reservas`);
  }

  public guardar(reserva: Reserva) {
    return this.http.doPost<Reserva, boolean>(`${environment.endpoint}/reserva`, reserva,
                                                this.http.optsName('crear/actualizar reservas'));
  }

  public eliminar(reserva: Reserva) {
    return this.http.doDelete<boolean>(`${environment.endpoint}/reserva/${reserva.idReserva}`);
  }

  public consultarUno(idReserva: number){
    return this.http.doGet<Reserva>(`${environment.endpoint}/reservas/${idReserva}`);
  }
}
