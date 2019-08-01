import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientesListRoutingModule } from './clientes-list-routing.module';
import {ClientesListComponent} from './clientes-list.component';
import {ClienteService} from '../../services/cliente.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {PrettyPrintPipe} from '../../pipes/pretty-print.pipe';


@NgModule({
  declarations: [
    ClientesListComponent,
    PrettyPrintPipe,
  ],
  imports: [
    CommonModule,
    NgbModule,
    ClientesListRoutingModule
  ],
  providers: [
    ClienteService
  ]
})
export class ClientesListModule { }
