import {Cliente} from '../clientes/cliente';
import {ItemFactura} from '../items-factura/itemFactura';

export class Factura {
  id: number;
  descripcion: string;
  observacion: string;
  cliente: Cliente;
  items: ItemFactura[];
  createdAt: Date;
}
