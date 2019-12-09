export class Usuario {
  private _id: number;
  private _username: string;
  private _password: string;
  private _email: string;
  private _enabled: boolean;
  private _roles = [];

  constructor() {
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

  get roles() {
    return this._roles;
  }

  set roles(value) {
    this._roles = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  toJSON() {
    return {
      id: this.id,
      username: this.username,
      password: this.password,
      enabled: this.enabled,
      email: this.email,
      roles: this.roles
    };
  }
}
