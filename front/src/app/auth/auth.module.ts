import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { AuthComponent } from './auth.component';
import {CookieService} from 'ngx-cookie-service';

@NgModule({
  declarations: [AuthComponent],
  exports: [
    AuthComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule
  ],
  providers: [
    CookieService
  ]
})
export class AuthModule { }
