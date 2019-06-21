import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientesFormRoutingModule } from './clientes-form-routing.module';
import {ClientesFormComponent} from './clientes-form.component';
import {FormsModule} from '@angular/forms';
import {BackButtonModule} from '../../common/back-button/back-button.module';

@NgModule({
  declarations: [
    ClientesFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ClientesFormRoutingModule,
    BackButtonModule
  ]
})
export class ClientesFormModule { }
