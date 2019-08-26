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

  private url = environment.api.url + 'auth';
  httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'}),
    withCredentials: true
  };
  redirectUrl: string;

  constructor(private http: HttpClient,
              private cs: CookieService,
              private router: Router) {
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
          this.cs.set('token', environment.api.token, undefined, '/', 'localhost', location.protocol === 'https:', 'Strict');
          return userData;
        }
      ),
      catchError(e => throwError(e))
    );
  }

  logout() {
    environment.api.token = '';
    this.cs.delete('token');
  }

  isLogged(): boolean {
    return !!environment.api.token || this.cs.check('token');
  }

  isAdmin(): boolean {
    return this.isLogged() && JSON.stringify(this.getData().roles).includes('ADMIN');
  }

  getUser(): Usuario {
    const user = new Usuario();
    user.username = this.getData().username;
    user.roles = this.getData().roles;
    return user;
  }

  getToken(): string {
    return this.cs.get('token');
  }

  private getData() {
    return JSON.parse(window.atob(this.cs.get('token').split('.')[1]));
  }

}
