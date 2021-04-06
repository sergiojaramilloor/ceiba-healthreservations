import { EditarReservaComponent } from './components/editar-reserva/editar-reserva.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CrearReservaComponent } from './components/crear-reserva/crear-reserva.component';
import { ListarReservaComponent } from './components/listar-reserva/listar-reserva.component';
import { ReservaComponent } from './components/reserva/reserva.component';

const routes: Routes = [
    {
        path: '',
        component: ReservaComponent,
        children:[
            {
                path: 'crear',
                component: CrearReservaComponent
            },
            {
                path: 'listar',
                component: ListarReservaComponent
            },
            {
                path: 'editar',
                component: EditarReservaComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class ReservaRoutingModule { }