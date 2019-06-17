import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {FacturasRoutingModule} from './facturas-routing.module';
import {FacturasComponent} from './facturas.component';
import {FacturasDetalleComponent} from './facturas-detalle/facturas-detalle.component';
import {FacturasFormComponent} from './facturas-form/facturas-form.component';

@NgModule({
  declarations: [
    FacturasComponent,
    FacturasDetalleComponent,
    FacturasFormComponent
  ],
  imports: [
    CommonModule,
    FacturasRoutingModule,

  ]
})
export class FacturasModule { }
