import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductosFormComponent} from './productos-form.component';

const rutas: Routes = [
  { path: '', component: ProductosFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(rutas)],
  exports: [RouterModule]
})
export class ProductosFormRoutingModule {}
