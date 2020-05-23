import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Usuario} from '../models/usuario';
import {catchError, map} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
              private cs: CookieService,
              private router: Router) {
  }

  private url = environment.api.url + 'auth';
  httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'}),
    withCredentials: true
  };
  redirectUrl: string;

  private static createHeaders(responseType: string = null) {
    return new HttpHeaders({
      'Content-type': 'application/json',
      Authorization: `Beaver ${environment.api.token}`,
      responseType: responseType
    });
  }

  private static createOptions(responseType: string = null) {
    return {
      withCredentials: true,
      headers: AuthService.createHeaders(responseType)
    };
  }

  register(usuario: Usuario): Observable<Usuario> | Usuario | any {
    return this.http.post<Usuario>(`${this.url}/register`, usuario.toJSON(), this.httpOptions).pipe(
      catchError(e => {
        return throwError(e);
      })
    );
  }

  login(username: string, password: string) {
    return this.http.post(`${this.url}/login`, {username, password}, this.httpOptions).pipe(
      map(
        (userData: any) => {
          environment.api.token = userData.jwtToken;
          this.cs.set('token', userData.jwtToken, undefined, '/', 'localhost', location.protocol === 'https:', 'Strict');
          this.httpOptions = AuthService.createOptions(null);
          return userData;
        }
      ),
      catchError(e => throwError(e))
    );
  }

  logout() {
    environment.api.token = '';
    this.cs.delete('token', '/');
  }

  isLogged(): boolean {
    return !!environment.api.token || this.cs.check('token');
  }

  isAdmin(): boolean {
    return this.hasRole('ADMIN');
  }

  hasRole(rol: string) {
    return this.isLogged() && JSON.stringify(this.getData().roles).includes(rol);
  }

  getUser(): Usuario {
    const user = new Usuario();
    user.username = this.getData().username;
    user.roles = this.getData().roles;
    user.clienteId = this.getData().clienteId;
    return user;
  }

  getToken(): string {
    return this.cs.get('token');
  }

  getOptions(): object {
    return this.httpOptions;
  }

  blob(url: string) {
    const options = AuthService.createOptions('blob');
    return this.http.get(url, options);
  }

  private getData() {
    return JSON.parse(window.atob(this.cs.get('token').split('.')[1]));
  }
}
