import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {environment as prod} from '../../environments/environment.prod';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  static getUrl() {
    return environment.api.url;
  }

  get<T>(url , options?: {
    headers?: HttpHeaders | {
      [header: string]: string | string[];
    };
    observe?: 'body';
    params?: HttpParams | {
      [param: string]: string | string[];
    };
    reportProgress?: boolean;
    responseType?: 'json';
    withCredentials?: boolean;
  }): Observable<T> {
    return this.http.get<T>(url, options);
  }

}
