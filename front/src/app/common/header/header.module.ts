import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HeaderRoutingModule } from './header-routing.module';
import {HeaderComponent} from './header.component';
import {AuthModule} from '../../auth/auth.module';

@NgModule({
  declarations: [HeaderComponent],
  entryComponents: [HeaderComponent],
  imports: [
    CommonModule,
    AuthModule,
    HeaderRoutingModule
  ]
})
export class HeaderModule {
  static entry = HeaderComponent;
}
