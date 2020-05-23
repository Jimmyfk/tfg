import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientesListComponent} from './clientes-list.component';
import {ClientesDetailsResolverService} from '../../services/resolver/clientes-details-resolver.service';
import {ClienteGuard} from '../../services/guards/cliente.guard';

const clientesRoutes: Routes = [
  {
    path: '',
    children: [
      {path: '', component: ClientesListComponent},
      {path: 'listar', component: ClientesListComponent},
      {path: 'nuevo', loadChildren: () => import('../clientes-form/clientes-form.module').then(m => m.ClientesFormModule)},
      {
        path: ':clienteId', loadChildren: () => import('../clientes-detalle/clientes-detalle.module').then(m => m.ClientesDetalleModule),
        resolve: { cliente: ClientesDetailsResolverService},
        canActivate: [ClienteGuard]
      },
      {
        path: 'editar/:clienteId', loadChildren: () => import('../clientes-form/clientes-form.module').then(m => m.ClientesFormModule),
        canActivate: [ClienteGuard]
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(clientesRoutes)],
  exports: [RouterModule]
})
export class ClientesListRoutingModule {
}
