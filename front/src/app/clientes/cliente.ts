export class Cliente {
  private _id: number;
  private _nombre: string;
  private _apellidos: string;
  private _createdAt: Date;
  private _email: string;
  private _foto: string;


  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get nombre(): string {
    return this._nombre;
  }

  set nombre(value: string) {
    this._nombre = value;
  }

  get apellidos(): string {
    return this._apellidos;
  }

  set apellidos(value: string) {
    this._apellidos = value;
  }

  get createdAt(): Date {
    return this._createdAt;
  }

  set createdAt(value: Date) {
    this._createdAt = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  get foto(): string {
    return this._foto;
  }

  set foto(value: string) {
    this._foto = value;
  }

  toJSON() {
    return {
      id: this.id,
      nombre: this.nombre,
      apellidos: this.apellidos,
      createdAt: this.createdAt,
      email: this.email,
      foto: this.foto
    };
  }
}
