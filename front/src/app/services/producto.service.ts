import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {Producto} from '../productos/producto';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private url = environment.baseUrl + 'productos';
  private httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'})
  };

  constructor(private http: HttpClient,
              private router: Router) {
  }

  buscarProductos(name: string) {
    return this.http.get(`${this.url}/search/${name}`).pipe(
      map((response: any) => {
        return response.map(item => {
          return item.nombre;
        });
      }));
  }

  getProducto(name: string) {
    return this.http.get<Producto>(`${this.url}/find/${name}`);
  }

  getById(id: number) {
    return this.http.get<Producto>(`${this.url}/id/${id}`);
  }

  getProductos() {
    return this.http.get<Producto[]>(`${this.url}/cargar`);
  }

  create(producto: Producto) {
    return this.http.post<Producto>(`${this.url}/create`, producto, this.httpOptions);
  }

  update(producto: Producto) {
    return this.http.put(`${this.url}`, producto, this.httpOptions);
  }

  delete(id: number) {
    return this.http.delete(`${this.url}/` + id, this.httpOptions);
  }
}
