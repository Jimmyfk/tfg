import {UsuarioWrapper} from './usuario-wrapper';

export class Usuario {
  id: number;
  username: string;
  password: string;
  enabled: boolean;
  authorities: string[] = [];

  copy(usuario: UsuarioWrapper) {
    this.id = usuario.id;
    this.username = usuario.username;
    this.password = usuario.password;
    this.enabled = usuario.enabled;
    this.authorities.push('USER');
  }
}
