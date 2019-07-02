import {Producto} from '../productos/producto';
import {OnInit} from '@angular/core';

export class ItemFactura implements OnInit {
  private _id: number;
  private _producto: Producto;
  private _precio: number;
  private _cantidad: number;
  private _importe: number;

  constructor(producto: Producto) {
    this._producto = producto;
    this._cantidad = 1;
    this._precio = producto.precio;
    this.setImporte();
  }

  ngOnInit(): void {
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get producto(): Producto {
    return this._producto;
  }

  set producto(value: Producto) {
    this._producto = value;
  }

  get cantidad(): number {
    return this._cantidad;
  }

  set cantidad(value: number) {
    this._cantidad = value;
  }

  get importe(): number {
    return this._importe;
  }

  set importe(value: number) {
    this._importe = value;
  }

  setImporte() {
    return this._importe = this._precio * this._cantidad;
  }

  get precio(): number {
    return this._precio;
  }

  set precio(value: number) {
    this._precio = value;
  }

  toJSON() {
    return {
      id: this.id,
      producto: this.producto,
      precio: this.precio,
      cantidad: this.cantidad,
      importe: this.importe
    };
  }
}
