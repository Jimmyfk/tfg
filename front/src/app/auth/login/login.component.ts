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
        this.usuario.authorities = JSON.parse(this.usuarioService.getRoles());
        const redirect = this.usuarioService.redirectUrl ? this.router.parseUrl(this.usuarioService.redirectUrl) : '/inicio';
        this.router.navigateByUrl(redirect).then();
      }
    }, () => {
      Swal.fire('Error', 'Usuario o contraseña incorrectos', 'error').then();
    });
  }

  ngOnInit(): void {
  }

}
