export class Producto {

  private _id: number;
  private _nombre: string;
  private _precio: number | string;
  private _createdAt: Date;

  get id(): number  {
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

  get precio(): number | string {
    return this._precio;
  }

  set precio(value: number | string) {
    this._precio = value;
  }

  get createdAt(): Date {
    return this._createdAt;
  }

  set createdAt(value: Date) {
    this._createdAt = value;
  }

  toJSON() {
    return {
      id: this.id,
      nombre: this.nombre,
      precio: this.precio,
      createdAt: this.createdAt
    };
  }
}
