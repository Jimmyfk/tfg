import {Router} from '@angular/router';
import swal from 'sweetalert2';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Cliente} from '../clientes/cliente.js';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {environment} from '../../environments/environment';


@Injectable()
export class ClienteService {

  private url = environment.baseUrl + 'clientes';
  private httpHeaders = new HttpHeaders({'Content-type': 'application/json'});

  mayus = (x: string): string => x.toUpperCase();

  constructor(private http: HttpClient,
              private router: Router) {
  }

  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.url).pipe(
      map(response => {
        response.forEach(cliente => {
          cliente.nombre = this.mayus(cliente.nombre);
        });
        return response;
      })
    );
  }

  getCliente(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.url}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['error', '404']).then(() =>
          swal.fire('Error', 'El cliente no existe', 'error')
        );
        return throwError(e);
      })
    );
  }

  create(cliente: Cliente): Observable<any> {
    return this.http.post<any>(this.url, cliente, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        if (e.status === 400) {
          return throwError(e);
        }

        swal.fire('Error al crear el cliente', e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  update(cliente: Cliente): Observable<any> {
    return this.http.put<any>(`${this.url}/${cliente.id}`, cliente, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        if (e.status === 400) {
          return throwError(e);
        }

        swal.fire('Error al actualizar el cliente', e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(cliente: Cliente): Observable<any> {
    return this.http.delete<Cliente>(`${this.url}/${cliente.id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        swal.fire('Error al eliminar el cliente', e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}
