import {Component, OnInit} from '@angular/core';
import {Usuario} from '../../models/usuario';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {SwalService} from '../../services/swal.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent implements OnInit {

  public usuario: Usuario = new Usuario();
  public titulo = 'Iniciar Sesión';

  constructor(private usuarioService: AuthService,
              private swal: SwalService,
              private router: Router) {
  }

  login() {
    this.usuarioService.login(this.usuario.username, this.usuario.password).subscribe(() => {
      if (this.usuarioService.isLogged()) {
        this.usuario.roles = this.usuarioService.getUser().roles;
        const redirect = this.usuarioService.redirectUrl ? this.router.parseUrl(this.usuarioService.redirectUrl) : '/inicio';
        this.router.navigateByUrl(redirect).then();
      }
    }, error => {
      switch (error.status) {
        case 0:
          this.swal.getCustomButton().fire('Error', 'Error al conectar con el servidor', 'error').then(() => console.log(error));
          break;
        case 404:
          this.swal.getCustomButton().fire('Error', 'Usuario o contraseña incorrectos', 'error').then();
          break;
        default:
          this.swal.getCustomButton().fire('Error', error.message, 'error').then(() => console.log(error));
      }
    });
  }

  ngOnInit(): void {
  }

}
