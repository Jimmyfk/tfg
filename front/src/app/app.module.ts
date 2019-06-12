import {ClientesFormComponent} from './clientes/clientes-form/clientes-form.component';
import {ClienteService} from './services/cliente.service';
import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {InicioComponent} from './inicio/inicio.component';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {ClientesComponent} from './clientes/clientes.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import localeEs from '@angular/common/locales/es';
import localeIt from '@angular/common/locales/it';
import localeRu from '@angular/common/locales/ru';
import {ClientesDetalleComponent} from './clientes/clientes-detalle/clientes-detalle.component';
import {FacturasComponent} from './facturas/facturas.component';
import {FacturasDetalleComponent} from './facturas/facturas-detalle/facturas-detalle.component';
import {FacturasFormComponent} from './facturas/facturas-form/facturas-form.component';

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
      ClientesComponent,
      ClientesFormComponent,
      ClientesDetalleComponent,
      FacturasComponent,
      FacturasDetalleComponent,
      FacturasFormComponent
   ],
   imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule

   ],
   providers: [
     ClienteService,
     {provide: LOCALE_ID, useValue: 'es' }
   ],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
