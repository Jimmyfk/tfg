import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductosListComponent } from './productos-list.component';
import {ProductosListRoutingModule} from './productos-list-routing.module';

@NgModule({
  declarations: [ProductosListComponent],
  imports: [
    CommonModule,
    ProductosListRoutingModule
  ]
})
export class ProductosListModule { }
