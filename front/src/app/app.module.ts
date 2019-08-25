import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import localeEs from '@angular/common/locales/es';
import localeIt from '@angular/common/locales/it';
import localeRu from '@angular/common/locales/ru';
import {AppRoutingModule} from './app-routing.module';
import {HttpInterceptorService} from './services/httpInterceptor.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LAZY_WIDGETS} from './services/lazy/lazy-tokens';
import {lazyWidgets, lazyArrayToObj} from './services/lazy/lazy-widget';
import {LazyloaderService} from './services/lazy/lazyloader.service';
import {provideRoutes} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';


registerLocaleData(localeFr, 'fr');
registerLocaleData(localeEs, 'es');
registerLocaleData(localeIt, 'it');
registerLocaleData(localeRu, 'ru');

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    {provide: LOCALE_ID, useValue: 'es'},
    {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true},
    {provide: LAZY_WIDGETS, useFactory: lazyArrayToObj},
    LazyloaderService, provideRoutes(lazyWidgets),
    CookieService
  ],
  exports: [],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
