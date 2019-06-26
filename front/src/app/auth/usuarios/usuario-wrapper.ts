export class UsuarioWrapper {
  id: number;
  username: string;
  password: string;
  confirmar: string;
  authorities: string[] = [];
  enabled: boolean;
}
