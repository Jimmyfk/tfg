import {Component, OnInit} from '@angular/core';
import {Factura} from '../factura';
import {ClienteService} from '../../services/cliente.service';
import {ActivatedRoute} from '@angular/router';
import {MatAutocompleteSelectedEvent} from '@angular/material';
import {FormControl} from '@angular/forms';
import {FacturaService} from '../../services/factura.service';
import {debounceTime} from 'rxjs/operators';
import {ItemFactura} from '../../items-factura/itemFactura';

@Component({
  selector: 'app-facturas-form',
  templateUrl: './facturas-form.component.html',
  styleUrls: ['./facturas-form.component.css']
})
export class FacturasFormComponent implements OnInit {

  public factura: Factura;

  buscar: FormControl = new FormControl();
  resultados = [];

  constructor(private clienteService: ClienteService,
              private facturaService: FacturaService,
              private rutaActiva: ActivatedRoute) {
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
      this.facturaService.buscarProductos(data).subscribe(response => {
        this.resultados = response;
      });
    });
  }

  select(event: MatAutocompleteSelectedEvent) {
    this.facturaService.getProducto(event.option.value).subscribe((response: any) => {
      if (!this.hasProducto(response.producto.id)) {
        this.factura.items.push(new ItemFactura(response.producto));
      }
      this.factura.calcularTotal();
    });
    console.log(this.factura);
  }

  private hasProducto(id: number): boolean {
    for (const {item, index} of this.toItemIndexes(this.factura.items)) {
      if (item.producto.id === id) {
        return true;
      }
    }
    return false;
  }

  private toItemIndexes<T>(a: T[]) {
    return a.map((item, index) => ({item, index}));
  }
}
