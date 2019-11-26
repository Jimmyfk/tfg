import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FacturasFormRoutingModule } from './facturas-form-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatAutocompleteModule, MatInputModule} from '@angular/material';
import {ClienteService} from '../../services/cliente.service';
import {FacturasFormComponent} from './facturas-form.component';
import {ProductoService} from '../../services/producto.service';
import {RoleGuard} from '../../services/guards/role.guard';
import {BackButtonModule} from '../../common/back-button/back-button.module';

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
    FacturasFormRoutingModule,
    BackButtonModule
  ],
  providers: [
    ClienteService,
    ProductoService,
    RoleGuard
  ]
})
export class FacturasFormModule { }
