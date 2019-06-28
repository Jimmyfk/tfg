import {Component, OnInit} from '@angular/core';
import {ProductoService} from '../../services/producto.service';
import {Producto} from '../producto';

@Component({
  selector: 'app-productos-list',
  templateUrl: './productos-list.component.html',
  styleUrls: []
})
export class ProductosListComponent implements OnInit {

  public productos: Producto[];

  constructor(private productoService: ProductoService) {
  }

  ngOnInit() {
    this.productos = [];
    this.productoService.getProductos().subscribe(
      (response: any) => {
        this.productos = response.productos;
      }
    );
  }

}
