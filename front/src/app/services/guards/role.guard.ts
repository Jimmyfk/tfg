import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '../auth.service';
import {reject} from 'q';
import {Usuario} from '../../models/usuario';
import {SwalService} from '../swal.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService,
              private swal: SwalService,
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
        resolve(false);
        this.router.navigate(['/login']).then(() =>
          this.swal.getCustomButton().fire({
            title: 'Error',
            text: 'No tienes permisos para ver esta página',
            type: 'error',
            imageUrl: ''
          })
        );
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
