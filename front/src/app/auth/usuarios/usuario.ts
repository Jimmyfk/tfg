export class Usuario {
  id: number;
  username: string;
  password: string;
  enabled: boolean;
  authorities: string[] = [];

  constructor() {
    this.authorities.push('USER');
  }
}
