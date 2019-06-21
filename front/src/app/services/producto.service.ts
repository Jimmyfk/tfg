import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {Producto} from '../productos/producto';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private url = environment.baseUrl + 'facturas';

  constructor(private http: HttpClient,
              private router: Router) {
  }

  buscarProductos(name: string) {
    return this.http.get(`${this.url}/cargar-productos/${name}`).pipe(
      map((response: any) => {
        return response.map(item => {
          return item.nombre;
        });
      }));
  }

  getProducto(name: string) {
    return this.http.get<Producto>(`${this.url}/producto/${name}`);
  }
}
