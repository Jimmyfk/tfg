import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {FooterComponent} from './footer.component';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [FooterComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([])
  ],
  entryComponents: [ FooterComponent ]
})
export class FooterModule {
  static entry = FooterComponent;
}
