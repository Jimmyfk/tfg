import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {FacturasRoutingModule} from './facturas-routing.module';
import {FacturasComponent} from './facturas.component';
import {FacturasDetalleComponent} from './facturas-detalle/facturas-detalle.component';
import {FacturasFormComponent} from './facturas-form/facturas-form.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ClienteService} from '../services/cliente.service';
import {MatAutocompleteModule, MatInputModule} from '@angular/material';
import {ProductosComponent} from '../productos/productos.component';
import {ItemsFacturaComponent} from '../items-factura/items-factura.component';

@NgModule({
  declarations: [
    FacturasComponent,
    FacturasFormComponent,
    FacturasDetalleComponent,
    ProductosComponent,
    ItemsFacturaComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatAutocompleteModule,
    FacturasRoutingModule
  ],
  providers: [
    ClienteService
  ]
})
export class FacturasModule {
}
