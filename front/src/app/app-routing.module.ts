import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './services/guards/auth.guard';


const routes: Routes = [
  {
    path: 'inicio',
    loadChildren: () => import('./common/inicio/inicio.module').then(mod => mod.InicioModule)
  },
  {
    path: 'clientes',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    canLoad: [AuthGuard],
    loadChildren: () => import('./clientes/clientes-list/clientes-list.module').then(mod => mod.ClientesListModule)
  },
  {
    path: 'factura',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    canLoad: [AuthGuard],
    loadChildren: () => import('./facturas/facturas.module').then(mod => mod.FacturasModule),
  },
  {path: '', redirectTo: 'inicio', pathMatch: 'full'},
  {
    path: 'error',
    loadChildren: () => import('./common/errores/errores.module').then(mod => mod.ErroresModule)
  },
  {
    path: 'register',
    loadChildren: () => import('./auth/form/form.module').then(m => m.FormModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./auth/login/login.module').then(m => m.LoginModule)
  },
  {
    path: 'productos',
    canActivate: [AuthGuard],
    canLoad: [AuthGuard],
    loadChildren: () => import('./productos/productos-list/productos-list.module').then(m => m.ProductosListModule)
  },
  {path: '**', redirectTo: 'error/404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    enableTracing: false,
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
