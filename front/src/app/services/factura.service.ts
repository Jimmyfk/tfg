import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Factura} from '../models/factura';
import {catchError} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import {SwalService} from './swal.service';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  private url = environment.baseUrl + 'facturas';
  private httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'})
  };

  constructor(private http: HttpClient,
              private router: Router,
              private swal: SwalService) {
  }

  getFacturas(id) {
    return this.http.get<Factura[]>(`${this.url}/ver/${id}`);
  }

  getDetalleFactura(id): Observable<Factura> {
    return this.http.get<any>(`${this.url}/${id}`).pipe(
      catchError(err => {
        this.router.navigate(['error', err.status]).then(() =>
          this.swal.fire('Error', err.error.mensaje, 'error'));
        return throwError(err);
      })
    );
  }

  create(factura: Factura): Observable<any> {
    return this.http.post<any>(`${this.url}/${factura.cliente.id}`, factura.toJSON(), this.httpOptions).pipe(
      catchError(e => {
        if (e.status === 400 || e.status === 500) {
          return throwError(e);
        }
        return this.swal.fire('Error al guardar la factura', e.error.error, 'error').then(() => throwError(e));
      })
    );
  }

  delete(factura: Factura) {
    return this.http.delete(`${this.url}/${factura.id}`).pipe(
      catchError(e => throwError(e))
    );
  }
}
