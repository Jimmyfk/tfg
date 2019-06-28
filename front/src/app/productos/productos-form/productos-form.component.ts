import {Component, OnInit} from '@angular/core';
import {Producto} from '../producto';
import {ProductoService} from '../../services/producto.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-productos-form',
  templateUrl: './productos-form.component.html',
  styleUrls: []
})
export class ProductosFormComponent implements OnInit {

  producto: Producto = new Producto();
  titulo = 'Nuevo Producto';

  constructor(private productoService: ProductoService,
              private rutaActiva: ActivatedRoute) {
  }

  ngOnInit() {
    this.cargarProducto();
    console.log(this.producto);
  }

  cargarProducto() {
    this.rutaActiva.params.subscribe(
      params => {
        const id = params.id;
        if (id) {
          this.titulo = 'Editar Producto';
          this.productoService.getById(id).subscribe((response: any) => {
            this.producto = response.producto;
          });
        }
      }
    );
  }

  save() {
    if (this.producto.id) {
      return this.update();
    }
    return this.create();
  }

  create() {

  }

  update() {

  }
}
