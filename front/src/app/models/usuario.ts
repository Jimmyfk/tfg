export class Usuario {
  private _id: number;
  private _username: string;
  private _password: string;
  private _enabled: boolean;
  private _authorities: string[] = [];

  constructor() {
    this._authorities.push('USER');
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get enabled(): boolean {
    return this._enabled;
  }

  set enabled(value: boolean) {
    this._enabled = value;
  }

  get authorities(): string[] {
    return this._authorities;
  }

  set authorities(value: string[]) {
    this._authorities = value;
  }

  toJSON() {
    return {
      id: this.id,
      username: this.username,
      password: this.password,
      enabled: this.enabled,
      authorities: this.authorities
    };
  }
}
