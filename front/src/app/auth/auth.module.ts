import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { AuthComponent } from './auth.component';
import {LoginModule} from './login/login.module';
import {LogoutModule} from './logout/logout.module';
import {RegisterModule} from './register/register.module';

@NgModule({
  declarations: [AuthComponent],
  exports: [
    AuthComponent
  ],
  imports: [
    CommonModule,
    LoginModule,
    LogoutModule,
    RegisterModule,
    AuthRoutingModule
  ]
})
export class AuthModule { }
