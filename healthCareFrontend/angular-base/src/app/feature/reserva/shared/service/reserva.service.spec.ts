import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ReservaService } from './reserva.service';
import { environment } from 'src/environments/environment';
import { HttpService } from 'src/app/core/services/http.service';
import { Reserva } from '../model/reserva';
import { HttpResponse } from '@angular/common/http';

describe('ReservaService', () => {
  let httpMock: HttpTestingController;
  let service: ReservaService;
  const apiEndpointReservaConsulta = `${environment.endpoint}/tiposFamilia`;
  const apiEndpointReserva = `${environment.endpoint}/reserva`;

  beforeEach(() => {
    const injector = TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReservaService, HttpService]
    });
    httpMock = injector.inject(HttpTestingController);
    service = TestBed.inject(ReservaService);
  });

  it('should be created', () => {
    const productService: ReservaService = TestBed.inject(ReservaService);
    expect(productService).toBeTruthy();
  });

  it('deberia listar productos', () => {
    const dummyReserva = [
      new Reserva(), new Reserva()
    ];
    service.consultar().subscribe(productos => {
      expect(productos.length).toBe(2);
      expect(productos).toEqual(dummyReserva);
    });
    const req = httpMock.expectOne(apiEndpointReservaConsulta);
    expect(req.request.method).toBe('GET');
    req.flush(dummyReserva);
  });

  it('deberia crear un producto', () => {
    const dummyReserva = new Reserva();
    service.guardar(dummyReserva).subscribe((respuesta) => {
      expect(respuesta).toEqual(true);
    });
    const req = httpMock.expectOne(apiEndpointReserva);
    expect(req.request.method).toBe('POST');
    req.event(new HttpResponse<boolean>({body: true}));
  });

  it('deberia eliminar un producto', () => {
    const dummyReserva = new Reserva();
    service.eliminar(dummyReserva).subscribe((respuesta) => {
      expect(respuesta).toEqual(true);
    });
    const req = httpMock.expectOne(`${apiEndpointReserva}/1`);
    expect(req.request.method).toBe('DELETE');
    req.event(new HttpResponse<boolean>({body: true}));
  });
});
