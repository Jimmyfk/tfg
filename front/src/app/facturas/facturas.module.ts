import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {FacturasRoutingModule} from './facturas-routing.module';
import {FacturasComponent} from './facturas.component';
import {FacturasDetalleComponent} from './facturas-detalle/facturas-detalle.component';
import {FacturasFormComponent} from './facturas-form/facturas-form.component';
import {FormsModule} from '@angular/forms';
import {ClienteService} from '../services/cliente.service';
import {MatAutocompleteModule, MatInputModule} from '@angular/material';

@NgModule({
  declarations: [
    FacturasComponent,
    FacturasFormComponent,
    FacturasDetalleComponent
  ],
  imports: [
    CommonModule,
    FacturasRoutingModule,
    FormsModule,
    MatInputModule,
    MatAutocompleteModule
  ],
  providers: [
    ClienteService
  ]
})
export class FacturasModule {
}
