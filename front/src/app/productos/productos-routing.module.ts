import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const productosRoutes: Routes = [
  {
    path: '',
    children: [
      { path: '', loadChildren: () => import('./productos-list/productos-list.module').then(m => m.ProductosListModule) },
      { path: 'listado', loadChildren: () => import('./productos-list/productos-list.module').then(m => m.ProductosListModule) },
      { path: 'nuevo', loadChildren: () => import('./productos-form/productos-form.module').then(m => m.ProductosFormModule) },
      { path: 'editar', loadChildren: () => import('./productos-form/productos-form.module').then(m => m.ProductosFormModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(productosRoutes)],
  exports: [RouterModule]
})
export class ProductosRoutingModule {}
