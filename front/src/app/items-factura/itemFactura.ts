import {Producto} from '../productos/producto';

export class ItemFactura {
  id: number;
  producto: Producto;
  cantidad: number;
  importe: number;
}
