import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientesDetalleRoutingModule } from './clientes-detalle-routing.module';
import { ClientesDetalleComponent } from './clientes-detalle.component';
import {BackButtonModule} from '../../common/back-button/back-button.module';

@NgModule({
  declarations: [
    ClientesDetalleComponent
  ],
  imports: [
    CommonModule,
    ClientesDetalleRoutingModule,
    BackButtonModule
  ]
})
export class ClientesDetalleModule { }
