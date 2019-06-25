import {Component, OnInit} from '@angular/core';
import {UsuarioWrapper} from '../usuario-wrapper';
import {Usuario} from '../usuario';
import {UsuarioService} from '../../services/usuario.service';
import swal from 'sweetalert2';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: []
})
export class RegisterComponent implements OnInit {

  public wrapper: UsuarioWrapper = new UsuarioWrapper();
  public usuario: Usuario = new Usuario();

  constructor(private usuarioService: UsuarioService,
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
