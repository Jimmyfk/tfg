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

  private static hasRole(user: Usuario, rol: string[]): boolean {
    for (const rl of user.roles) {
      if (rol.includes(rl.rol) || rol.filter(r => r === rl.rol)) {
        return true;
      }
    }
    return false;
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
      if (!roles || RoleGuard.hasRole(user, roles)) {
        resolve(true);
      } else {
        resolve(false);
        this.router.navigate([this.authService.isLogged() ? '/inicio' : '/login']).then(() =>
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
      this.router.navigate(['/inicio']).then(() => this.swal.getCustomButton().fire('Error', err, 'error'));
      return err;
    });
  }

}
