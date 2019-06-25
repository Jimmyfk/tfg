import {InicioComponent} from './common/inicio/inicio.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';


const routes: Routes = [
  { path: '', pathMatch: 'full', component: InicioComponent },
  { path: 'inicio', component: InicioComponent },
  {
    path: 'clientes',
    loadChildren: () => import('./clientes/clientes-list/clientes-list.module').then(mod => mod.ClientesListModule)
  },
  {
    path: 'factura',
    loadChildren: () => import('./facturas/facturas.module').then(mod => mod.FacturasModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./auth/login/login.module').then(mod => mod.LoginModule)
  },
  {
    path: 'logout',
    loadChildren: () => import('./auth/logout/logout.module').then(mod => mod.LogoutModule)
  },
  {
    path: 'register',
    loadChildren: () => import('./auth/register/register.module').then(mod => mod.RegisterModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
