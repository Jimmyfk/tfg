import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FacturasDetalleComponent} from './facturas-detalle/facturas-detalle.component';
import {FacturasFormComponent} from './facturas-form/facturas-form.component';

const facturaRoutes: Routes = [
  {
    path: '',
    children: [
      {path: 'factura/:id', component: FacturasDetalleComponent},
      {path: 'factura/nueva/:id', component: FacturasFormComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(facturaRoutes)],
  exports: [RouterModule]
})
export class FacturasRoutingModule {
}
