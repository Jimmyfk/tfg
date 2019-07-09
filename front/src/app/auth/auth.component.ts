import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';
import {SwalService} from '../services/swal.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: [],
})
export class AuthComponent implements OnInit {

  constructor(private usuarioService: AuthService,
              private swal: SwalService,
              private router: Router) {
  }

  ngOnInit() {
  }

  logout() {
    this.swal.getCustomButton().fire({
      title: '¿Cerrar sesión?',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'No'
    }).then(result => {
      if (result.value) {
        this.usuarioService.logout();
        this.router.navigate(['inicio']).then();
      }
    });
  }

  isLogged() {
    return this.usuarioService.isLogged();
  }

}
