import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Cliente} from '../models/cliente';
import {EMPTY, Observable, of} from 'rxjs';
import {ClienteService} from './cliente.service';
import {mergeMap, take} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ClientesDetailsResolverService implements Resolve<Cliente> {

  constructor(private clienteService: ClienteService,
              private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Cliente> | Promise<Cliente> | Cliente {
    const id = route.paramMap.get('id');

    return this.clienteService.getCliente(id).pipe(
      take(1),
      mergeMap(cliente => {
        if (cliente) {
          return of(cliente);
        } else {
          this.router.navigate(['/clientes']).then(() => EMPTY);
        }
      })
    );
  }
}
