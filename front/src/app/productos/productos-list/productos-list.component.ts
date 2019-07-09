import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ProductoService} from '../../services/producto.service';
import {Producto} from '../../models/producto';
import {SwalService} from '../../services/swal.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-productos-list',
  templateUrl: './productos-list.component.html',
  styleUrls: []
})
export class ProductosListComponent implements OnInit {

  public productos: Producto[];

  constructor(private productoService: ProductoService,
              private authService: AuthService,
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
          () => {
            this.productos = this.productos.filter(prods => prods !== producto);
          },
          error => {
            swalButtons.fire('Error', error.error.error, 'error').then();
            console.log(error);
          }
        );
      }
    });
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  tracker(item) {
    return item.id;
  }

}
