import {Component, OnInit} from '@angular/core';
import {Usuario} from '../usuarios/usuario';
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
    this.usuarioService.login(this.usuario.username, this.usuario.password).subscribe(response => {
      this.usuario.authorities = JSON.parse(sessionStorage.getItem('authorities'));
      this.router.navigate(['/inicio']);
    }, error => {
      Swal.fire('Error', 'Usuario o contraseña incorrectos', 'error').then();
    });
  }

  ngOnInit(): void {
  }

}
