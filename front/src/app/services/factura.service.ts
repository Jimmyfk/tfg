import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  private url = environment.baseUrl + 'factura';
  private httpHeaders = new HttpHeaders({'Content-type': 'application/json'});

  constructor() { }
}
