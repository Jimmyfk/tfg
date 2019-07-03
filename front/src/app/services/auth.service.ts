import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Usuario} from '../models/usuario';
import {catchError, map} from 'rxjs/operators';
import {throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = environment.baseUrl + 'auth';
  httpOptions = {
    headers: new HttpHeaders({'Content-type': 'application/json'})
  };
  redirectUrl: string;

  constructor(private http: HttpClient,
              private router: Router) {
  }

  register(usuario: Usuario) {
    return this.http.post(`${this.url}/register`, usuario.toJSON(), this.httpOptions).pipe(
      catchError(e => {
        return throwError(e);
      })
    );
  }

  login(username: string, password: string) {
    return this.http.post(`${this.url}/login`, {username, password}, this.httpOptions).pipe(
      map(
        (userData: any) => {
          localStorage.setItem('username', username);
          const token = 'Bearer ' + userData.jwtToken;
          const data = JSON.parse(window.atob(token.split('.')[1]));
          localStorage.setItem('token', token);
          localStorage.setItem('authorities', JSON.stringify(data.authorities));
          return userData;
        }
      ),
      catchError(e => {
        return throwError(e);
      })
    );
  }

  logout() {
    localStorage.removeItem('username');
    localStorage.removeItem('token');
    localStorage.removeItem('authorities');
  }

  isLogged() {
    const user = localStorage.getItem('username');
    return !(user === null);
  }

  isAdmin() {
    return localStorage.getItem('authorities').includes('ADMIN');
  }

}
