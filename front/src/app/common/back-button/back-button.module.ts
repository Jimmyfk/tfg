import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BackButtonComponent } from './back-button.component';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [
    BackButtonComponent
  ],
  exports: [
    BackButtonComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild([])
  ],
  entryComponents: [
    BackButtonComponent
  ]
})
export class BackButtonModule {
  static entry = BackButtonComponent;
}
