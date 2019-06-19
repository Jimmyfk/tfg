import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientesListRoutingModule } from './clientes-list-routing.module';
import {ClientesListComponent} from './clientes-list.component';
import {ClienteService} from '../../services/cliente.service';

@NgModule({
  declarations: [
    ClientesListComponent,
  ],
  imports: [
    CommonModule,
    ClientesListRoutingModule
  ],
  providers: [
    ClienteService
  ]
})
export class ClientesListModule { }
