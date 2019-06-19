import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientesDetalleRoutingModule } from './clientes-detalle-routing.module';
import {ClientesDetalleComponent} from './clientes-detalle.component';

@NgModule({
  declarations: [
    ClientesDetalleComponent
  ],
  imports: [
    CommonModule,
    ClientesDetalleRoutingModule
  ]
})
export class ClientesDetalleModule { }
