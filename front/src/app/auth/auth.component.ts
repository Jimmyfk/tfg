import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';
import Swal from 'sweetalert2';
import {Router} from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: [],
})
export class AuthComponent implements OnInit {

  constructor(private usuarioService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
  }

  logout() {
    Swal.fire({
      title: '¿Cerrar sesión?',
      type: 'warning',
      showCancelButton: true,
      confirmButtonClass: 'btn btn-sm',
      confirmButtonText: 'Sí',
      cancelButtonClass: 'btn btn-sm',
      cancelButtonText: 'No'
    }).then(result => {
      if (result.value) {
        this.usuarioService.logout();
        this.router.navigate(['inicio']).then(() =>  null);
      }
    });
  }

  isLogged() {
    return this.usuarioService.isLogged();
  }

}
