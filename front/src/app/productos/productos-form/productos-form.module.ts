import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductosFormComponent } from './productos-form.component';
import {ProductosFormRoutingModule} from './productos-form-routing.module';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [ProductosFormComponent],
  imports: [
    CommonModule,
    FormsModule,
    ProductosFormRoutingModule
  ]
})
export class ProductosFormModule { }
