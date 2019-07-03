import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Cliente} from '../models/cliente.js';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {SwalService} from './swal.service';


@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private url = environment.baseUrl + 'clientes';
  private httpHeaders = new HttpHeaders({'Content-type': 'application/json'});

  constructor(private http: HttpClient,
              private router: Router,
              private swalService: SwalService) {
  }

  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.url).pipe(
      catchError(err => throwError(err))
    );
  }

  getCliente(id): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.url}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['error', '404']).then(() =>
          this.swalService.fire('Error', 'El cliente no existe', 'error')
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
        this.swalService.fire('Error al crear el cliente', e.error.error, 'error').then(
          () => throwError(e)
        );
      })
    );
  }

  update(cliente: Cliente): Observable<any> {
    console.log(cliente);
    console.log(cliente.apellidos);
    return this.http.put<any>(`${this.url}/${cliente.id}`, cliente, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        if (e.status === 400) {
          return throwError(e);
        }
        this.swalService.fire('Error al actualizar el cliente', e.error.error, 'error').then(() =>
          throwError(e));
      })
    );
  }

  delete(cliente: Cliente): Observable<any> {
    return this.http.delete<Cliente>(`${this.url}/${cliente.id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        this.swalService.fire('Error al eliminar el cliente', e.error.error, 'error').then();
        return throwError(e);
      })
    );
  }
}
