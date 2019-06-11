import {FormComponent} from './clientes/form/form.component';
import {ClientesComponent} from './clientes/clientes.component';
import {InicioComponent} from './inicio/inicio.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientesDetalleComponent} from './clientes/clientes-detalle/clientes-detalle.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/inicio' },
  { path: 'inicio', component: InicioComponent },
  { path: 'clientes', component: ClientesComponent },
  { path: 'clientes/formulario', component: FormComponent },
  { path: 'clientes/:id', component: ClientesDetalleComponent },
  { path: 'clientes/formulario/:id', component: FormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const RUTAS = [
  FormComponent,
  ClientesComponent,
  InicioComponent,
  ClientesDetalleComponent
];
