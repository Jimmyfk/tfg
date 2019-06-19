import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FacturasFormComponent} from './facturas-form.component';

const routes: Routes = [
  { path: '', component: FacturasFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacturasFormRoutingModule { }
