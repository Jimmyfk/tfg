import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: [],
})
export class AuthComponent implements OnInit {

  constructor(private usuarioService: AuthService) {
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
    }).then((result) => {
      if (result.value) {
        this.usuarioService.logout();
        Swal.fire('Sesión cerrada');
      }
    });
  }

  isLogged() {
    return this.usuarioService.isLogged();
  }

}
