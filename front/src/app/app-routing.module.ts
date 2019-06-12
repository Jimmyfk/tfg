import {ClientesFormComponent} from './clientes/clientes-form/clientes-form.component';
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
  { path: 'clientes/formulario', component: ClientesFormComponent },
  { path: 'clientes/:id', component: ClientesDetalleComponent },
  { path: 'clientes/formulario/:id', component: ClientesFormComponent },
  {
    path: 'facturas',
    loadChildren: () => import ('./facturas/facturas.module').then(mod => mod.FacturasModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [
  ClientesFormComponent,
  ClientesComponent,
  InicioComponent,
  ClientesDetalleComponent,
  FacturasComponent,
  FacturasDetalleComponent,
  FacturasFormComponent
];
