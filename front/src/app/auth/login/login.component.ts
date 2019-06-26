import {Component, OnInit} from '@angular/core';
import {Usuario} from '../usuarios/usuario';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent implements OnInit {

  public usuario: Usuario = new Usuario();

  constructor(private usuarioService: AuthService,
              private router: Router) {
  }

  login() {
    this.usuarioService.login(this.usuario.username, this.usuario.password).subscribe(response => {
      this.usuario.authorities = sessionStorage.getItem('authorities') as string[];
      this.router.navigate(['/inicio']);
    });
  }

  ngOnInit(): void {
  }

}
