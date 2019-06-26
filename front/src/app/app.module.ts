import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule, NgModuleFactory, NgModuleFactoryLoader, SystemJsNgModuleLoader} from '@angular/core';
import {AppComponent} from './app.component';
import {InicioComponent} from './common/inicio/inicio.component';
import {HeaderComponent} from './common/header/header.component';
import {FooterComponent} from './common/footer/footer.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import localeEs from '@angular/common/locales/es';
import localeIt from '@angular/common/locales/it';
import localeRu from '@angular/common/locales/ru';
import {AppRoutingModule} from './app-routing.module';
import {AuthModule} from './auth/auth.module';



registerLocaleData(localeFr, 'fr');
registerLocaleData(localeEs, 'es');
registerLocaleData(localeIt, 'it');
registerLocaleData(localeRu, 'ru');

@NgModule({
  declarations: [
    AppComponent,
    InicioComponent,
    HeaderComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    AuthModule
  ],
  providers: [
    {provide: LOCALE_ID, useValue: 'es'},
    SystemJsNgModuleLoader
  ],
  exports: [
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
