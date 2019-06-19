import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const facturaRoutes: Routes = [
  {
    path: '',
    children: [
      { path: ':id', loadChildren: () => import('./facturas-detalle/factura-detalles.module').then(m => m.FacturaDetallesModule) },
      { path: 'nueva/:id', loadChildren: () => import('./facturas-form/facturas-form.module').then(m => m.FacturasFormModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(facturaRoutes)],
  exports: [RouterModule]
})
export class FacturasRoutingModule {
}
