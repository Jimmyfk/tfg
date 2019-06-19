import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ClientesListComponent} from './clientes-list.component';

const clientesRoutes: Routes = [
  {
    path: '',
    children: [
      { path: '', component: ClientesListComponent, pathMatch: 'full'},
      { path: 'listar', component: ClientesListComponent },
      { path: 'nuevo', loadChildren: () => import('../clientes-form/clientes-form.module').then(m => m.ClientesFormModule) },
      { path: ':id', loadChildren: () => import('../clientes-detalle/clientes-detalle.module').then(m => m.ClientesDetalleModule) },
      { path: 'editar/:id', loadChildren: () => import('../clientes-form/clientes-form.module').then(m => m.ClientesFormModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(clientesRoutes)],
  exports: [RouterModule]
})
export class ClientesListRoutingModule { }
