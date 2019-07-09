import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductosFormComponent} from './productos-form.component';
import {RoleGuard} from '../../services/guards/role.guard';

const rutas: Routes = [
  { path: '', component: ProductosFormComponent, canActivate: [RoleGuard], data: {roles: ['ROLE_ADMIN']} }
];

@NgModule({
  imports: [RouterModule.forChild(rutas)],
  exports: [RouterModule]
})
export class ProductosFormRoutingModule {}
