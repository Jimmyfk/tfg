import { InjectionToken } from '@angular/core';

export type LAZY_MODULES = {
  header: string;
};

export const lazyMap: LAZY_MODULES = {
  header: 'src/app/common/header/header.module#HeaderModule'
};

export const LAZY_MODULES_MAP = new InjectionToken('LAZY_MODULES_MAP', {
  factory: () => lazyMap
});
