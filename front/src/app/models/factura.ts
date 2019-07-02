import {Cliente} from './cliente';
import {ItemFactura} from './itemFactura';

export class Factura {
  private _id: number;
  private _descripcion: string;
  private _observacion: string;
  private _cliente: Cliente;
  private _items: ItemFactura[];
  private _createdAt: Date;
  private _total: number;

  constructor(cliente: Cliente) {
    this._cliente = cliente;
    this._items = [];
    this._total = 0;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get descripcion(): string {
    return this._descripcion;
  }

  set descripcion(value: string) {
    this._descripcion = value;
  }

  get observacion(): string {
    return this._observacion;
  }

  set observacion(value: string) {
    this._observacion = value;
  }

  get cliente(): Cliente {
    return this._cliente;
  }

  set cliente(value: Cliente) {
    this._cliente = value;
  }

  get items(): ItemFactura[] {
    return this._items;
  }

  set items(value: ItemFactura[]) {
    this._items = value;
  }

  get createdAt(): Date {
    return this._createdAt;
  }

  set createdAt(value: Date) {
    this._createdAt = value;
  }

  get total(): number {
    return this._total;
  }

  set total(value: number) {
    this._total = value;
  }

  calcularTotal() {
    this._total = 0;
    for (const item of this._items) {
      this._total += item.importe;
    }
    this._total = Number(this._total.toFixed(2));
  }

  toJSON() {
    return {
      id: this.id,
      cliente: this.cliente,
      descripcion: this.descripcion,
      observacion: this.observacion,
      items: this.items,
      total: this.total
    };
  }

}
