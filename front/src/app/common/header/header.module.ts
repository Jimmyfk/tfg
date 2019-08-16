import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderRoutingModule } from './header-routing.module';
import {HeaderComponent} from './header.component';

@NgModule({
  declarations: [HeaderComponent],
  entryComponents: [HeaderComponent],
  exports: [
    HeaderComponent
  ],
  imports: [
    CommonModule,
    HeaderRoutingModule
  ]
})
export class HeaderModule {
  static entry = HeaderComponent;
}
