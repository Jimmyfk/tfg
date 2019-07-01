import {Producto} from '../productos/producto';
import {OnInit} from '@angular/core';

export class ItemFactura implements OnInit {
  id: number;
  producto: Producto;
  cantidad: number;
  importe: number;

  constructor(producto: Producto) {
    this.producto = producto;
    this.cantidad = 1;
    this.setImporte();
  }

  ngOnInit(): void {
  }

  setImporte() {
    this.importe = this.producto.precio * this.cantidad;
  }
}
