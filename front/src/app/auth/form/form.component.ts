import {Component, OnInit} from '@angular/core';
import {Usuario} from '../../models/usuario';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {SwalService} from '../../services/swal.service';

@Component({
  selector: 'app-register',
  templateUrl: './form.component.html',
  styleUrls: []
})
export class FormComponent implements OnInit {

  public usuario: Usuario;
  public pw2: string;
  esValido: boolean;

  constructor(private usuarioService: AuthService,
              private swal: SwalService,
              private router: Router) {
  }

  ngOnInit() {
    this.usuario = new Usuario();
    this.pw2 = '';
    this.isValid();
  }

  register() {
    function getError(error: string) {
      if (error.includes('Username_UNIQUE')) {
        return 'El nombre de usuario está cogido';
      }
      return 'Error al realizar la consulta';
    }

    this.usuarioService.register(this.usuario).subscribe(response => {
        this.router.navigate(['/inicio']).then(() =>
          this.swal.getCustomButton().fire('', decodeURIComponent(escape(response.mensaje)), 'success').then(() => console.log(response)));
      },
      err => {
        this.swal.getCustomButton().fire('Error', getError(err.error.error)).then(() => console.error(err));
      });
  }

  isValid() {
    this.esValido = this.usuario.password === this.pw2 && !!this.usuario.username && this.usuario.username.length > 2;
  }
}
