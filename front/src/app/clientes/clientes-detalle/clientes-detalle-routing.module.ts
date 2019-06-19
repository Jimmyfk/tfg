import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ClientesDetalleComponent} from './clientes-detalle.component';

const routes: Routes = [
  { path: '', component: ClientesDetalleComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientesDetalleRoutingModule { }
