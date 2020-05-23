import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: []
})
export class InicioComponent implements OnInit {

  constructor(private authService: AuthService,
              private router: Router ) {
  }

  ngOnInit() {
    if (this.authService.hasRole('CLIENTE')) {
      const usuario = this.authService.getUser();
      this.router.navigate(['clientes', usuario.clienteId]);
    }
  }

}
