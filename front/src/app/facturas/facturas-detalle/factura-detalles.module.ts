import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FacturaDetallesRoutingModule } from './factura-detalles-routing.module';
import {FacturaService} from '../../services/factura.service';
import {FacturasDetalleComponent} from './facturas-detalle.component';

@NgModule({
  declarations: [
    FacturasDetalleComponent
  ],
  imports: [
    CommonModule,
    FacturaDetallesRoutingModule
  ],
  providers: [
    FacturaService
  ]
})
export class FacturaDetallesModule { }
