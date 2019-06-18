import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Factura} from '../facturas/factura';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  private url = environment.baseUrl + 'facturas';
  private httpHeaders = new HttpHeaders({'Content-type': 'application/json'});

  constructor(private http: HttpClient,
              private router: Router) {
  }

  getFacturas(id: number) {
    return this.http.get<Factura[]>(`${this.url}/ver/${id}`);
  }

  getDetalleFactura(id: number) {
    return this.http.get<Factura>(`${this.url}/${id}`);
  }
}
