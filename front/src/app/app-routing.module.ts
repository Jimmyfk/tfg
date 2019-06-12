import {FormComponent} from './clientes/form/form.component';
import {ClientesComponent} from './clientes/clientes.component';
import {InicioComponent} from './inicio/inicio.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientesDetalleComponent} from './clientes/clientes-detalle/clientes-detalle.component';
import {FacturasDetalleComponent} from './facturas/facturas-detalle/facturas-detalle.component';
import {FacturasComponent} from './facturas/facturas.component';
import {FacturasFormComponent} from './facturas/facturas-form/facturas-form.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/inicio' },
  { path: 'inicio', component: InicioComponent },
  { path: 'clientes', component: ClientesComponent },
  { path: 'clientes/formulario', component: FormComponent },
  { path: 'clientes/:id', component: ClientesDetalleComponent },
  { path: 'clientes/formulario/:id', component: FormComponent },
  { path: 'factura/:id', component: FacturasDetalleComponent },
  { path: 'factura/formulario/:id', component: FacturasFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [
  FormComponent,
  ClientesComponent,
  InicioComponent,
  ClientesDetalleComponent,
  FacturasComponent,
  FacturasDetalleComponent,
  FacturasFormComponent
];
