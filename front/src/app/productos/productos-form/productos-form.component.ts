import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {Producto} from '../../models/producto';
import {ProductoService} from '../../services/producto.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {SwalService} from '../../services/swal.service';
import {LazyloaderService} from '../../services/lazy/lazyloader.service';

@Component({
  selector: 'app-productos-form',
  templateUrl: './productos-form.component.html',
  styleUrls: []
})
export class ProductosFormComponent implements OnInit, OnDestroy, AfterViewInit {

  producto: Producto = new Producto();
  titulo = 'Nuevo Producto';
  errores: string[];
  subscription: Subscription;
  @ViewChild('botonAtras', {read: ViewContainerRef, static: false})
  botonAtras: ViewContainerRef;

  constructor(private productoService: ProductoService,
              private rutaActiva: ActivatedRoute,
              private swal: SwalService,
              private loader: LazyloaderService,
              private router: Router) {
  }

  ngOnInit() {
    this.cargarProducto();
  }

  cargarProducto() {
    this.subscription = this.rutaActiva.params.subscribe(
      params => {
        const id = params.id;
        if (id) {
          this.titulo = 'Editar Producto';
          this.productoService.getById(id).subscribe(response => this.producto = response);
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
      (response: any) => {
        this.router.navigate(['productos']).then(() =>
          this.swal.getCustomButton().fire('', response.mensaje, 'info')
        );
      }, error => {
        this.errores = error.error.errores as string[];
        this.swal.getCustomButton().fire('Error al guardar el producto', error.error.mensaje, 'error');
      }
    );
  }

  update() {
    this.productoService.update(this.producto).subscribe(
      (response: any) => {
        this.router.navigate(['productos']).then(() =>
          this.swal.getCustomButton().fire('', decodeURIComponent(escape(response.mensaje)), 'info')
        );
      }, error => {
        this.errores = error.error.errores as string[];
        this.swal.getCustomButton().fire('Error al modificar el producto', error.error.mensaje, 'error');
      }
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngAfterViewInit(): void {
    this.loader.load('back-btn', this.botonAtras).then();
  }

  setPrecio =  (val)  => {
    this.producto.precio = Number(val);
  }
}
