import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FormComponent} from './form.component';
import {AuthGuard} from '../../services/guards/auth.guard';

const routes: Routes = [
  {
    path: '', component: FormComponent, canActivate: [AuthGuard], canActivateChild: [AuthGuard], children: [
      {path: 'admin', component: FormComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FormRoutingModule {
}
