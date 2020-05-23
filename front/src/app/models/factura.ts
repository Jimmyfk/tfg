import {Cliente} from './cliente';
import {ItemFactura} from './itemFactura';

export class Factura {
  private _id: number;
  public descripcion: string;
  private _observacion: string;
  private _cliente: Cliente;
  private _items: ItemFactura[];
  private _createdAt: Date;
  private _total: number;

  constructor(cliente?: Cliente) {
    if (cliente) {
      this._cliente = cliente;
      this._items = [];
      this.descripcion = '';
    }
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
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

  public push(value: ItemFactura) {
    this.items.push(value);
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

  /**
   * método que comprueba si un producto existe en la factura, incrementa la cantidad del item cuando existe
   * @param productoId id del producto a comprobar
   * @param cantidad cantidad de items a comprobar, para sumar si ya existen
   * @return true si la factura ya tiene el producto, false si no
   */
  checkProducto(productoId: number, cantidad: number) {
    for (const item of this.items) {
      if (item.producto.id === productoId) {
        item.cantidad +=  cantidad;
        return true;
      }
    }
    return false;
  }

  public stringify() {
    return JSON.stringify(this).replace(/_/g, '');
  }

  public toJSON() {
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
