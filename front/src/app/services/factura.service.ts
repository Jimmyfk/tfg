import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Factura} from '../facturas/factura';
import {catchError} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  private url = environment.baseUrl + 'facturas';
  httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'})
  };

  constructor(private http: HttpClient,
              private router: Router) {
  }

  getFacturas(id: number) {
    return this.http.get<Factura[]>(`${this.url}/ver/${id}`);
  }

  getDetalleFactura(id: number): Observable<Factura> {
    return this.http.get<any>(`${this.url}/${id}`).pipe(
      catchError(err => {
        this.router.navigate(['error', err.status]).then(() =>
          swal.fire('Error', err.error.mensaje, 'error'));
        return throwError(err);
      })
    );
  }

  create(factura: Factura): Observable<any> {
    return this.http.post<any>(`${this.url}/nueva/${factura.cliente.id}`, factura, this.httpOptions).pipe(
      catchError(e => {
        if (e.status === 400 || e.status === 500) {
          return throwError(e);
        }
        swal.fire('Error al guardar la factura', e.error.error, 'error').then();
        return throwError(e);
      })
    );
  }
}
