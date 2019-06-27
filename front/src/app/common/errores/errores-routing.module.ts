import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: 'error',
    children: [
      { path: '404', loadChildren: () => import('./404/e404.module').then(m => m.E404Module) },
      { path: '**', redirectTo: '404'}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ErroresRoutingModule {
}
