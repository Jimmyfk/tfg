import {Component, OnInit} from '@angular/core';
import {Usuario} from '../../models/usuario';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent implements OnInit {

  public usuario: Usuario = new Usuario();
  public titulo = 'Iniciar Sesión';

  constructor(private usuarioService: AuthService,
              private router: Router) {
  }

  login() {
    this.usuarioService.login(this.usuario.username, this.usuario.password).subscribe(() => {
      if (this.usuarioService.isLogged()) {
        this.usuario.authorities = this.usuarioService.getRoles();
        const redirect = this.usuarioService.redirectUrl ? this.router.parseUrl(this.usuarioService.redirectUrl) : '/inicio';
        this.router.navigateByUrl(redirect).then();
      }
    }, error => {
      if (error.status === 404) {
        Swal.fire('Error', 'Usuario o contraseña incorrectos', 'error').then();
      } else {
        Swal.fire('Error', error.message, 'error').then();
      }
    });
  }

  ngOnInit(): void {
    if (this.usuarioService.isLogged()) {
      this.router.navigateByUrl('inicio').then();
    }
  }

}
