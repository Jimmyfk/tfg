import {Component, OnInit} from '@angular/core';
import {Usuario} from '../usuarios/usuario';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent implements OnInit {

  public usuario: Usuario = new Usuario();

  constructor(private usuarioService: AuthService) {
  }

  ngOnInit() {
  }

  login() {
    this.usuarioService.login(this.usuario.username, this.usuario.password).subscribe(response => {
      console.log(response);
      console.log(this.usuario);
    });
  }

}
