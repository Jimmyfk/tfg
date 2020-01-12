import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {Factura} from '../../models/factura';
import {ClienteService} from '../../services/cliente.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MatAutocompleteSelectedEvent} from '@angular/material';
import {FormControl, Validators} from '@angular/forms';
import {FacturaService} from '../../services/factura.service';
import {debounceTime, take} from 'rxjs/operators';
import {ItemFactura} from '../../models/itemFactura';
import swal from 'sweetalert2';
import {ProductoService} from '../../services/producto.service';
import {interval, Observable, Subscription} from 'rxjs';
import {LazyloaderService} from '../../services/lazy/lazyloader.service';
import {Producto} from '../../models/producto';

@Component({
  selector: 'app-facturas-form',
  templateUrl: './facturas-form.component.html'
})
export class FacturasFormComponent implements OnInit, OnDestroy, AfterViewInit {

  constructor(private clienteService: ClienteService,
              private facturaService: FacturaService,
              private productoService: ProductoService,
              private rutaActiva: ActivatedRoute,
              private loader: LazyloaderService,
              private router: Router) {
  }

  public factura: Factura;
  item: ItemFactura;
  @ViewChild('botonAtras', {read: ViewContainerRef, static: false})
  botonAtras: ViewContainerRef;

  buscar: FormControl = new FormControl();
  cantidad: FormControl = new FormControl(1, Validators.min(1));
  resultados = [];
  errores = [];
  subcripciones: Subscription[] = [];
  hayProductos: boolean;
  hayClientes: boolean;
  puedeCrearFactura: boolean;

  private static decode(cadena: string): string {
    return decodeURIComponent(escape(cadena));
  }

  ngOnInit() {
    this.instanciarFactura();
    this.autocomplete();
    this.checkProductosYClientes();
  }

  instanciarFactura() {
    this.subcripciones[0] = this.rutaActiva.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.clienteService.getCliente(id).subscribe(cliente => {
          this.factura = new Factura(cliente);
        });
      }
    });
  }

  autocomplete() {
    this.subcripciones[1] = this.buscar.valueChanges.pipe(debounceTime(400)).subscribe(data => {
      if (data.toString().length > 0) {
        this.productoService.buscarProductos(data).subscribe(response => {
          this.resultados = response;
        });
      }
    });
  }

  select(value: string) {
    this.productoService.getProducto(value).subscribe(response => {
        this.item = new ItemFactura(response as Producto);
    });
  }

  pushItem() {
    if (!this.factura.checkProducto(this.item.producto.id, this.cantidad.value)) {
      this.item.cantidad = this.cantidad.value;
      this.factura.items.push(this.item);
      this.item = null;
    }
    this.factura.calcularTotal();
    this.buscar.reset();
    this.cantidad.setValue(1);
  }

  removeItem(it: ItemFactura) {
    for (const item of this.factura.items) {
      if (it.producto.id === item.producto.id) {
        this.factura.items.splice(this.factura.items.indexOf(it, 1));
        break;
      }
    }
    this.factura.calcularTotal();
  }

  create() {
    this.facturaService.create(this.factura).subscribe(
      response => {
        this.router.navigate(['/clientes', this.factura.cliente.id]).then(() =>
          swal.fire('Nueva factura', FacturasFormComponent.decode(response.mensaje), 'success'));
      },
      response => {
        this.errores = response.error.errores as string[];
      }
    );
  }

  ngOnDestroy(): void {
    for (const sub of this.subcripciones) {
      sub.unsubscribe();
    }
  }

  ngAfterViewInit(): void {
    if (this.botonAtras) {
      this.loader.load('back-btn', this.botonAtras).then();
    } else {
      const sub = interval(250).subscribe(() => {
        this.ngAfterViewInit();
        sub.unsubscribe();
      });
    }
  }

  log(): void {
    console.log(this.factura);
  }

  checkProductosYClientes() {
    this.productoService.existenProductos().subscribe(res => this.hayProductos = res.existenProductos);
    this.clienteService.existenClientes().subscribe(res => this.hayClientes = res.existenClientes);
    this.checkPuedeCrearFactura();
  }

  checkPuedeCrearFactura() {
    if (this.hayProductos === undefined || this.hayClientes === undefined) {
      interval(150).subscribe(() => this.checkPuedeCrearFactura());
      return;
    }
    this.puedeCrearFactura = this.hayClientes === true && this.hayProductos === true;
  }
}
