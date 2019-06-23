import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', loadChildren: () => import('./clientes-list/clientes-list.module').then(m => m.ClientesListModule) },
      { path: 'listar', loadChildren: () => import('./clientes-list/clientes-list.module').then(m => m.ClientesListModule)}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientesRoutingModule { }
