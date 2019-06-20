import {Producto} from '../productos/producto';
import {OnInit} from '@angular/core';

export class ItemFactura implements OnInit {
  id: number;
  producto: Producto;
  cantidad: number;

  constructor(producto: Producto) {
    this.producto = producto;
    this.cantidad = 1;
  }

  ngOnInit(): void {
    this.calcularImporte();
  }

  calcularImporte() {
    return this.producto.precio * this.cantidad;
  }
}
