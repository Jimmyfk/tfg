import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FacturasDetalleComponent} from './facturas-detalle.component';

const routes: Routes = [
  { path: '', component: FacturasDetalleComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacturaDetallesRoutingModule { }
