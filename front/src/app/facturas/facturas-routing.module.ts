import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FacturasDetalleComponent} from './facturas-detalle/facturas-detalle.component';

const routes: Routes = [
  { path: '',  component: FacturasDetalleComponent },
  { path: 'clientes/facturas/:id', component: FacturasDetalleComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacturasRoutingModule { }
