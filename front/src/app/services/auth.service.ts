import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Usuario} from '../auth/usuarios/usuario';
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

  login(username: string, password: string) {
    return this.http.post(`${this.url}/login`, {username, password}, this.httpOptions).pipe(
      map(
        (userData: any) => {
          sessionStorage.setItem('username', username);
          const token = 'Bearer ' + userData.jwtToken;
          sessionStorage.setItem('token', token);
          console.log(sessionStorage);
          return userData;
        }
      ),
      catchError(e => {
        return throwError(e);
      })
    );
  }

  logout() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('token');
  }

  isLogged() {
    const user = sessionStorage.getItem('username');
    console.log(!(user === null));
    return !(user === null);
  }

}
