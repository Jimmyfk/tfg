import {Component, OnDestroy, OnInit} from '@angular/core';
import {Usuario} from '../../models/usuario';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './form.component.html',
  styleUrls: []
})
export class FormComponent implements OnInit, OnDestroy {

  public usuario: Usuario;
  public pw2: string;
  sub: Subscription;

  constructor(private usuarioService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
    this.usuario = new Usuario();
    this.pw2 = null;
  }

  register() {
    this.sub = this.usuarioService.register(this.usuario).subscribe((response) => {
        this.router.navigate(['/inicio']).then(() => console.log(response));
      },
      () => {
        Swal.fire('Error', 'El nombre de usuario ya está cogido').then();
      });
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

}
