import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProductosListComponent} from './productos-list.component';

const rutas: Routes = [
  {
    path: '',
    children: [
      {path: '', component: ProductosListComponent},
      {path: 'listar', component: ProductosListComponent},
      {
        path: 'nuevo',
        loadChildren: () => import('../productos-form/productos-form.module').then(m => m.ProductosFormModule)
      },
      {
        path: 'editar/:id',
        loadChildren: () => import('../productos-form/productos-form.module').then(m => m.ProductosFormModule)
      },
      { path: '**', redirectTo: 'error/404'}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(rutas)],
  exports: [RouterModule]
})
export class ProductosListRoutingModule {
}
