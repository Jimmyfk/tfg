import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FacturasFormComponent} from './facturas-form.component';
import {RoleGuard} from '../../services/guards/role.guard';

const routes: Routes = [
  {
    path: '',
    component: FacturasFormComponent,
    canActivate: [RoleGuard],
    data: {roles: ['ROLE_ADMIN']}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacturasFormRoutingModule {
}
