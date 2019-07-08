import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Usuario} from '../models/usuario';
import {catchError, map} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = environment.baseUrl + 'auth';
  httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'}),
    withCredentials: true
  };
  redirectUrl: string;

  constructor(private http: HttpClient,
              private cs: CookieService,
              private router: Router) {
  }

  register(usuario: Usuario) {
    return this.http.post(`${this.url}/register/false`, usuario.toJSON(), this.httpOptions).pipe(
      catchError(e => {
        return throwError(e);
      })
    );
  }

  login(username: string, password: string) {
    return this.http.post(`${this.url}/login`, {username, password}, this.httpOptions).pipe(
      map(
        (userData: any) => {
          this.cs.set('token', userData.cookie.value, undefined, '/', undefined, location.protocol === 'https:', 'Strict');
          return userData;
        }
      ),
      catchError(e => throwError(e))
    );
  }

  logout() {
    this.cs.delete('token');
  }

  isLogged() {
    return this.cs.check('token');
  }

  isAdmin() {
   return JSON.stringify(this.getData().roles).includes('ADMIN');
  }

  getUser(): Usuario {
    const user = new Usuario();
    user.username = this.getData().username;
    user.roles = this.getData().roles;
    return user;
  }


  private getData() {
    const token = this.cs.get('token');
    return JSON.parse(window.atob(token.split('.')[1]));
  }

}
