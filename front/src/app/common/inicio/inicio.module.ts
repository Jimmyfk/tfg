import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {InicioComponent} from './inicio.component';
import {InicioRoutingModule} from './inicio-routing.module';

@NgModule({
  declarations: [InicioComponent],
  imports: [
    CommonModule,
    InicioRoutingModule
  ],
  exports: [InicioComponent]
})
export class InicioModule {}
