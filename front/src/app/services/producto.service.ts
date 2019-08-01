import {Injectable} from '@angular/core';
import {catchError, map} from 'rxjs/operators';
import {Producto} from '../models/producto';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable, throwError} from 'rxjs';
import {SwalService} from './swal.service';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private url = environment.baseUrl + 'productos';
  private httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'})
  };

  constructor(private http: HttpClient,
              private router: Router,
              private swal: SwalService) {
  }

  buscarProductos(name: string) {
    return this.http.get(`${this.url}/search/${name}`).pipe(
      map((response: any) => {
        return response.map(item => {
          return item.nombre;
        });
      }));
  }

  getProducto(name: string): Observable<Producto> | Producto | any {
    return this.http.get<Producto>(`${this.url}/find/${name}`);
  }

  getById(id: number): Observable<Producto> | Producto | any {
    return this.http.get<Producto>(`${this.url}/id/${id}`);
  }

  getProductos(): Observable<Producto[]> | Producto[] | any {
    return this.http.get<Producto[]>(`${this.url}`).pipe(
      catchError(err => throwError(err)));
  }

  create(producto: Producto): Observable<Producto> | Producto | any {
    return this.http.post<Producto>(`${this.url}`, producto.toJSON(), this.httpOptions).pipe(
      catchError(err => {
        return throwError(err);
      })
    );
  }

  update(producto: Producto): Observable<Producto> | Producto | any {
    return this.http.put<Producto>(`${this.url}/${producto.id}`, producto, this.httpOptions).pipe(
      catchError(err => throwError(err))
    );
  }

  delete(id: number) {
    return this.http.delete(`${this.url}/` + id, this.httpOptions);
  }
}
