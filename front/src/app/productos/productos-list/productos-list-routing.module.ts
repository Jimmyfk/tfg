import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductosListComponent} from './productos-list.component';

const rutas: Routes = [
  { path: '', component: ProductosListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(rutas)],
  exports: [RouterModule]
})
export class ProductosListRoutingModule {}
