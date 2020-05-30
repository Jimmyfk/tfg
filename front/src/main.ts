// Módulo principal del proyecto, se encarga de cargar el AppModule
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}
// Con esta instrucción se carga el módulo principal
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
