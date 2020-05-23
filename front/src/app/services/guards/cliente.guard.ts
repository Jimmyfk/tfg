import { Injectable } from '@angular/core';
// tslint:disable-next-line:max-line-length
import {
  CanActivate,
  CanActivateChild,
  CanLoad,
  Route,
  UrlSegment,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router
} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from '../auth.service';
import {Usuario} from '../../models/usuario';
import {SwalService} from '../swal.service';

@Injectable({
  providedIn: 'root'
})
export class ClienteGuard implements CanActivate, CanActivateChild, CanLoad {

  constructor(private authService: AuthService,
              private swal: SwalService,
              private router: Router) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const user: Usuario = this.authService.getUser();
    // id del cliente
    const clienteId = next.params.clienteId;


    // si el usuario es el cliente o el admistrador le dejamos pasar
    if (user.clienteId == clienteId || this.authService.isAdmin()) {
      return true;
    }

    this.swal.getCustomButton().fire('', '');
  }
  canActivateChild(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return true;
  }
  canLoad(
    route: Route,
    segments: UrlSegment[]): Observable<boolean> | Promise<boolean> | boolean {
    return true;
  }
}
