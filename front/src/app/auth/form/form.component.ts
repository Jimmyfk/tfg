import {Component, OnInit} from '@angular/core';
import {UsuarioWrapper} from '../usuarios/usuario-wrapper';
import {Usuario} from '../usuarios/usuario';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './form.component.html',
  styleUrls: []
})
export class FormComponent implements OnInit {

  public wrapper: UsuarioWrapper = new UsuarioWrapper();
  public usuario: Usuario = new Usuario();

  constructor(private usuarioService: AuthService,
              private router: Router) {
  }

  ngOnInit() {

  }

  register() {
    this.usuario.copy(this.wrapper);
    console.log(this.usuario);
    this.usuarioService.register(this.usuario).subscribe(response => {
      this.router.navigate(['/inicio']);
    });
  }

}
