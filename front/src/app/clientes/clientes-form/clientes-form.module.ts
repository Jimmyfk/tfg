import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientesFormRoutingModule } from './clientes-form-routing.module';
import {ClientesFormComponent} from './clientes-form.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    ClientesFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ClientesFormRoutingModule
  ]
})
export class ClientesFormModule { }
