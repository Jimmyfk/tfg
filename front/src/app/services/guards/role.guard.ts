import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '../auth.service';
import {reject} from 'q';
import {Usuario} from '../../models/usuario';

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
      if (!roles || this.hasRole(user, roles)) {
        resolve(true);
      } else {
        console.log('false 2');
        console.log(user.roles.includes(roles));
        resolve(false);
        this.router.navigate(['/login']).then();
      }
    }).catch(err => {
      reject(err).then();
      this.router.navigate(['/login']).then();
      return err;
    });
  }

  private hasRole(user: Usuario, rol: string): boolean {
    for (const rl of user.roles) {
     if (rl.rol.includes(rol)) {
       return true;
     }
    }
    return false;
  }

}
