import {Component, OnInit} from '@angular/core';
import {ProductoService} from '../../services/producto.service';
import {Producto} from '../producto';
import {SwalService} from '../../services/swal.service';

@Component({
  selector: 'app-productos-list',
  templateUrl: './productos-list.component.html',
  styleUrls: []
})
export class ProductosListComponent implements OnInit {

  public productos: Producto[];

  constructor(private productoService: ProductoService,
              private swal: SwalService) {
  }

  ngOnInit() {
    this.productos = [];
    this.productoService.getProductos().subscribe(
      (response: any) => {
        this.productos = response.productos;
      }
    );
  }

  delete(producto: Producto) {
    const swalButtons = this.swal.getCustomButton();
    swalButtons.fire({
      title: 'Confirmar',
      text: 'Eliminar ' + producto.nombre + '?',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí'
    }).then(result => {
      if (result.value) {
        this.productoService.delete(producto.id).subscribe(
          response => {
            console.log(response);
            this.productos = this.productos.filter(prods => prods !== producto);
          },
          error => {
            swalButtons.fire('Error', error.error.error, 'error').then();
            console.log(error);
          }
        );
      } else if (result.dismiss) {
        swalButtons.fire('Cancelado', 'No se eliminará el producto', 'info').then();
      }
    });
  }

}
