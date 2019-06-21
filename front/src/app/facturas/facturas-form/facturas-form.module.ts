import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FacturasFormRoutingModule } from './facturas-form-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule, MatInputModule} from '@angular/material';
import {ClienteService} from '../../services/cliente.service';
import {FacturasFormComponent} from './facturas-form.component';
import {ProductoService} from '../../services/producto.service';

@NgModule({
  declarations: [
    FacturasFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatAutocompleteModule,
    FacturasFormRoutingModule
  ],
  providers: [
    ClienteService,
    ProductoService
  ]
})
export class FacturasFormModule { }
