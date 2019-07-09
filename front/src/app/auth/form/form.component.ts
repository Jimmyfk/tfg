import {Component, OnInit} from '@angular/core';
import {Usuario} from '../../models/usuario';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './form.component.html',
  styleUrls: []
})
export class FormComponent implements OnInit {

  public usuario: Usuario;
  public pw2: string;

  constructor(private usuarioService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
    this.usuario = new Usuario();
    this.pw2 = null;
  }

  register() {
    this.usuarioService.register(this.usuario).subscribe((response) => {
        this.router.navigate(['/inicio']).then(() => console.log(response));
      },
      () => {
        Swal.fire('Error', 'El nombre de usuario ya está cogido').then();
      });
  }
}
