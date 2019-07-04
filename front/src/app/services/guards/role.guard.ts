import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '../auth.service';
import {reject} from 'q';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService,
              private router: Router) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean> {
    return new Promise<boolean>(resolve => {
      if (!this.authService.isLogged()) {
        resolve(false);
      }
      const user = this.authService.getUser();
      const roles = next.data.roles;
      if (!roles || user.authorities.toString().indexOf(roles) !== -1) {
        resolve(true);
      } else {
        resolve(false);
        this.router.navigate(['/login']).then();
      }
    }).catch(err => {
      reject(err).then();
      console.log('rejected');
      this.router.navigate(['/login']).then();
      return err;
    });
  }

}
