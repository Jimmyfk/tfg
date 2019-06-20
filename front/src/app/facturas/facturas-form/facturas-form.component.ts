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

  private hasProducto(id: number): boolean {
    for (const item of this.factura.items) {
      if (item.producto.id === id) {
        item.cantidad++;
        return true;
      }
    }
    return false;
  }
}
