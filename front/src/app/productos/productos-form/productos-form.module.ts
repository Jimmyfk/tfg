import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductosFormComponent } from './productos-form.component';
import {ProductosFormRoutingModule} from './productos-form-routing.module';
import {FormsModule} from '@angular/forms';
import {BackButtonModule} from '../../common/back-button/back-button.module';

@NgModule({
  declarations: [ProductosFormComponent],
  imports: [
    CommonModule,
    FormsModule,
    ProductosFormRoutingModule,
    BackButtonModule
  ]
})
export class ProductosFormModule { }
