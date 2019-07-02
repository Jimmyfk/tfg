import {Component, OnInit} from '@angular/core';
import {Producto} from '../producto';
import {ProductoService} from '../../services/producto.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-productos-form',
  templateUrl: './productos-form.component.html',
  styleUrls: []
})
export class ProductosFormComponent implements OnInit {

  producto: Producto = new Producto();
  titulo = 'Nuevo Producto';
  errores: string[];

  constructor(private productoService: ProductoService,
              private rutaActiva: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.cargarProducto();
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
    this.productoService.create(this.producto).subscribe(
      response => {
        console.log(response);
        this.router.navigate(['productos']).then();
      }, error => {
        console.log(error);
        this.errores = error.error.errores as string[];
      }
    );
  }

  update() {
    this.productoService.update(this.producto).subscribe(
      response => {
        console.log(response);
        this.router.navigate(['productos']).then();
      }, error => {
        console.log(error);
      }
    );
  }
}
