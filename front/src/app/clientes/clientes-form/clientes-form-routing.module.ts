import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientesFormComponent} from './clientes-form.component';
import {RoleGuard} from '../../services/guards/role.guard';

const routes: Routes = [
  {
    path: '',
    component: ClientesFormComponent,
    canActivate: [RoleGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE CLIENTE']}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientesFormRoutingModule {
}
