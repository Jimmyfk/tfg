import {Component, OnInit} from '@angular/core';
import {Factura} from '../../models/factura';
import {ClienteService} from '../../services/cliente.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MatAutocompleteSelectedEvent} from '@angular/material';
import {FormControl} from '@angular/forms';
import {FacturaService} from '../../services/factura.service';
import {debounceTime} from 'rxjs/operators';
import {ItemFactura} from '../../models/itemFactura';
import swal from 'sweetalert2';
import {ProductoService} from '../../services/producto.service';

@Component({
  selector: 'app-facturas-form',
  templateUrl: './facturas-form.component.html'
})
export class FacturasFormComponent implements OnInit {

  public factura: Factura;

  buscar: FormControl = new FormControl();
  resultados = [];
  errores = [];

  constructor(private clienteService: ClienteService,
              private facturaService: FacturaService,
              private productoService: ProductoService,
              private rutaActiva: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.instanciarFactura();
    this.autocomplete();
  }

  instanciarFactura() {
    this.rutaActiva.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.clienteService.getCliente(id).subscribe(cliente => {
          this.factura = new Factura(cliente);
        });
      }
    });
  }

  autocomplete() {
    this.buscar.valueChanges.pipe(debounceTime(400)).subscribe(data => {
      this.productoService.buscarProductos(data).subscribe(response => {
        this.resultados = response;
      });
    });
  }

  select(event: MatAutocompleteSelectedEvent) {
    this.productoService.getProducto(event.option.value).subscribe(response => {
      if (!this.hasProducto(response.id)) {
        this.factura.items.push(new ItemFactura(response));
      }
      this.factura.calcularTotal();
      this.buscar.setValue('');
    });
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
          swal.fire('Nueva factura', this.decode(response.mensaje), 'success'));
      },
      response => {
        this.errores = response.error.message as string[];
        console.error(this.errores);
        console.error('Código de error: ' + response.status);
      }
    );
  }

  private hasProducto(id: number): boolean {
    for (const item of this.factura.items) {
      if (item.producto.id === id) {
        item.cantidad++;
        return true;
      }
    }
    return false;
  }

  private decode(cadena: string): string {
    return decodeURIComponent(escape(cadena));
  }
}
