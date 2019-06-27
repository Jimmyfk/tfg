import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';


const routes: Routes = [
  { path: 'inicio', loadChildren: () => import('./common/inicio/inicio.module').then(mod => mod.InicioModule) },
  {
    path: 'clientes',
    loadChildren: () => import('./clientes/clientes-list/clientes-list.module').then(mod => mod.ClientesListModule)
  },
  {
    path: 'factura',
    loadChildren: () => import('./facturas/facturas.module').then(mod => mod.FacturasModule)
  },
  { path: '', redirectTo: 'inicio', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
