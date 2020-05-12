import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Cliente} from '../models/cliente';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {SwalService} from './swal.service';


@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private url = environment.api.url + 'clientes';
  private httpHeaders = new HttpHeaders({'Content-type': 'application/json'});

  constructor(private http: HttpClient,
              private router: Router,
              private swalService: SwalService) {
  }

  getClientes(): Observable<Cliente[]> | Cliente[] | any {
    return this.http.get<Cliente[]>(this.url).pipe(
      catchError(err => throwError(err))
    );
  }

  getXml() {
    return this.http.get(this.url + '/xml', {
      headers: new HttpHeaders().set('Content-Type', 'text/xml'),
      responseType: 'text'
    }).pipe(
      catchError(e => throwError(e))
    );
  }

  getCliente(id): Observable<Cliente> | Cliente | any {
    return this.http.get<Cliente>(`${this.url}/${id}`).pipe(
      catchError(e => throwError(e))
    );
  }

  create(cliente: Cliente, pw: string): Observable<Cliente> | Cliente | any {
    return this.http.post<Cliente>(this.url + '?password=' + pw, cliente, {headers: this.httpHeaders}).pipe(
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

  update(cliente: Cliente): Observable<Cliente> | Cliente | any {
    console.log(cliente);
    console.log(cliente.apellidos);
    return this.http.put<Cliente>(`${this.url}/${cliente.id}`, cliente, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        if (e.status === 400) {
          return throwError(e);
        }
        this.swalService.fire('Error al actualizar el cliente', e.error.error, 'error').then(() =>
          throwError(e));
      })
    );
  }

  delete(cliente: Cliente): Observable<Cliente> | Cliente | any {
    return this.http.delete<Cliente>(`${this.url}/${cliente.id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        this.swalService.fire('Error al eliminar el cliente', e.error.message, 'error').then();
        return throwError(e);
      })
    );
  }

  existenClientes() {
    return this.http.get<any>(`${this.url}/existen`).pipe(catchError(e => throwError(e))
    );
  }
}
