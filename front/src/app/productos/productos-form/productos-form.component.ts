import {Component, OnDestroy, OnInit} from '@angular/core';
import {Producto} from '../../models/producto';
import {ProductoService} from '../../services/producto.service';
import {ActivatedRoute, Router} from '@angular/router';
import {takeWhile} from 'rxjs/operators';

@Component({
  selector: 'app-productos-form',
  templateUrl: './productos-form.component.html',
  styleUrls: []
})
export class ProductosFormComponent implements OnInit, OnDestroy {

  producto: Producto = new Producto();
  titulo = 'Nuevo Producto';
  errores: string[];
  subscription: boolean;

  constructor(private productoService: ProductoService,
              private rutaActiva: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.cargarProducto();
    this.subscription = true;
  }

  cargarProducto() {
    this.rutaActiva.params.pipe(takeWhile(() => this.subscription)).subscribe(
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

  ngOnDestroy(): void {
    this.subscription = false;
  }
}
