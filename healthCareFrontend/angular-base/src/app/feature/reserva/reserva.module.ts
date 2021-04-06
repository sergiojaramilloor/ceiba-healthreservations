import { NgModule } from '@angular/core';

import { ReservaRoutingModule } from './reserva-routing.module';
import { CrearReservaComponent } from './components/crear-reserva/crear-reserva.component';
import { ListarReservaComponent } from './components/listar-reserva/listar-reserva.component';
import { ReservaComponent } from './components/reserva/reserva.component';
import { SharedModule } from '@shared/shared.module';
import { ReservaService } from './shared/service/reserva.service';
import { EditarReservaComponent } from './components/editar-reserva/editar-reserva.component';

@NgModule({
    declarations:[
        CrearReservaComponent,
        ListarReservaComponent,
        ReservaComponent,
        EditarReservaComponent
    ],
    imports: [
        ReservaRoutingModule,
        SharedModule
    ],
    providers:[ReservaService]
})
export class ReservaModule { }