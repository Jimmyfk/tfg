import {Component, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';

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
    this.usuarioService.logout();
  }

  isLogged() {
    return this.usuarioService.isLogged();
  }

}
