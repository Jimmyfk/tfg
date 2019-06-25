import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Usuario} from '../auth/usuario';
import {catchError} from 'rxjs/operators';
import {throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private url = environment.baseUrl + 'auth';
  httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'})
  };

  constructor(private http: HttpClient,
              private router: Router) {
  }

  register(usuario: Usuario) {
    return this.http.post(`${this.url}/register`, usuario, this.httpOptions).pipe(
      catchError(e => {
        return throwError(e);
      })
    );
  }
}
