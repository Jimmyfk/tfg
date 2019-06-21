import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', loadChildren: () => import('./clientes-list/clientes-list.module').then(m => m.ClientesListModule) },
      { path: 'listar', loadChildren: () => import('./clientes-list/clientes-list.module').then(m => m.ClientesListModule)},
      { path: 'nuevo', loadChildren: () => import('./clientes-form/clientes-form.module').then(m => m.ClientesFormModule) },
      { path: ':id', loadChildren: () => import('./clientes-detalle/clientes-detalle.module').then(m => m.ClientesDetalleModule) },
      { path: 'editar/:id', loadChildren: () => import('./clientes-form/clientes-form.module').then(m => m.ClientesFormModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientesRoutingModule { }
