import {InicioComponent} from './inicio/inicio.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';


const routes: Routes = [
  { path: '', pathMatch: 'full', component: InicioComponent },
  { path: 'inicio', component: InicioComponent },
  {
    path: 'clientes',
    loadChildren: () => import('./clientes/clientes.module').then(mod => mod.ClientesModule)
  },
  {
    path: 'facturas',
    loadChildren: () => import('./facturas/facturas.module').then(mod => mod.FacturasModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
