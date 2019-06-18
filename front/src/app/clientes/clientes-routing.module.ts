import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ClientesFormComponent} from './clientes-form/clientes-form.component';
import {ClientesDetalleComponent} from './clientes-detalle/clientes-detalle.component';
import {ClientesComponent} from './clientes.component';

const routes: Routes = [
  { path: '', component: ClientesComponent },
  { path: 'formulario', component: ClientesFormComponent },
  { path: ':id', component: ClientesDetalleComponent },
  { path: 'formulario/:id', component: ClientesFormComponent },
  { path: 'facturas/:id', loadChildren: '../facturas/facturas.module#FacturasModule' },
  { path: 'facturas/formulario/:id', loadChildren: '../facturas/facturas.module#FacturasModule' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientesRoutingModule { }
