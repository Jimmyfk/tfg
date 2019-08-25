import {NgModuleFactory, Type} from '@angular/core';

export const lazyWidgets: { path: string, loadChildren: () => Promise<NgModuleFactory<any> | Type<any>> }[] = [
  {
    path: 'header',
    loadChildren: () => import('../../common/header/header.module').then(m => m.HeaderModule)
  },
  {
    path: 'auth',
    loadChildren: () => import('../../auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'footer',
    loadChildren: () => import('../../common/footer/footer.module').then(m => m.FooterModule)
  },
  {
    path: 'back-btn',
    loadChildren: () => import('../../common/back-button/back-button.module').then(m => m.BackButtonModule)
  }
];

export function lazyArrayToObj() {
  const result = {};
  for (const w of lazyWidgets) {
    result[w.path] = w.loadChildren;
  }
  return result;
}
