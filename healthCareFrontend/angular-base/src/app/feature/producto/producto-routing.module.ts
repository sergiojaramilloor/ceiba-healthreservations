import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CrearProductoComponent } from './components/crear-producto/crear-producto.component';
import { ListarProductoComponent } from './components/listar-producto/listar-producto.component';
import { BorrarProductoComponent } from './components/borrar-producto/borrar-producto.component';
import { ProductoComponent } from './components/producto/producto.component';


const routes: Routes = [
  {
    path: '',
    component: ProductoComponent,
    children: [
      {
        path: 'crear-producto',
        component: CrearProductoComponent
      },
      {
        path: 'listar-producto',
        component: ListarProductoComponent
      },
      {
        path: 'borrar-producto',
        component: BorrarProductoComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductoRoutingModule { }
