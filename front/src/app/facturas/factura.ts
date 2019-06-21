import {Cliente} from '../clientes/cliente';
import {ItemFactura} from '../items-factura/itemFactura';

export class Factura {
  id: number;
  descripcion: string;
  observacion: string;
  cliente: Cliente;
  items: ItemFactura[];
  createdAt: Date;
  total: number;

  constructor(cliente: Cliente) {
    this.cliente = cliente;
    this.items = [];
    this.total = 0;
  }

  calcularTotal() {
    this.total = 0;
    for (const item of this.items) {
      this.total += item.calcularImporte();
    }
    this.total = Number(this.total.toFixed(2));
  }

}
