import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FacturasFormComponent} from './facturas-form.component';
import {RoleGuard} from '../../services/guards/role.guard';
import {ClienteGuard} from '../../services/guards/cliente.guard';

const routes: Routes = [
  {
    path: '',
    component: FacturasFormComponent,
    canActivate: [RoleGuard, ClienteGuard],
    data: {roles: ['ROLE_ADMIN', 'ROLE_CLIENTE']}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacturasFormRoutingModule {
}
