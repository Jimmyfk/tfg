import {RouterModule, Routes} from '@angular/router';
import {InicioComponent} from './inicio.component';
import {NgModule} from '@angular/core';

const routes: Routes = [
  {path: '', component: InicioComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InicioRoutingModule {
}
