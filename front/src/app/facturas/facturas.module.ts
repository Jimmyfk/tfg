import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {FacturasRoutingModule} from './facturas-routing.module';
import {BackButtonModule} from '../common/back-button/back-button.module';


@NgModule({
  declarations: [ ],
  imports: [
    CommonModule,
    FacturasRoutingModule,
    BackButtonModule
  ]
})
export class FacturasModule {
}
