import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  CanLoad,
  Route,
  Router,
  RouterStateSnapshot,
  UrlSegment,
  UrlTree
} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '../auth.service';
import {SwalService} from '../swal.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild, CanLoad {

  constructor(private authService: AuthService,
              private swal: SwalService,
              private router: Router) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (state.url.indexOf('login') !== -1 && state.url.indexOf('register') !== -1) {
      return this.checkLogin(state.url);
    }
    return !this.authService.isLogged();
  }

  canActivateChild(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    console.log('activate child');
    return this.canActivate(next, state);
  }

  checkLogin(url: string) {
    if (this.authService.isLogged()) {
      return true;
    }
    this.authService.redirectUrl = url.includes('login') ? undefined : url;
    this.router.navigate(['login']).then(() => this.swal.fire('No has iniciado sesión', '', 'error'));
    return false;
  }

  canLoad(route: Route, segments: UrlSegment[]): Observable<boolean> | Promise<boolean> | boolean {
    return this.checkLogin(`/${route.path}`);
  }
}
