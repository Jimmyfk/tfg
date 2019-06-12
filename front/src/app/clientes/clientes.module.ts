import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientesRoutingModule } from './clientes-routing.module';
import {ClientesComponent} from './clientes.component';
import {ClientesDetalleComponent} from './clientes-detalle/clientes-detalle.component';
import {ClientesFormComponent} from './clientes-form/clientes-form.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    ClientesComponent,
    ClientesDetalleComponent,
    ClientesFormComponent,
  ],
  imports: [
    CommonModule,
    ClientesRoutingModule,
    FormsModule
  ]
})
export class ClientesModule { }
