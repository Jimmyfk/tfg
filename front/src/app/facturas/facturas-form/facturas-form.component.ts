import {Component, OnInit} from '@angular/core';
import {Factura} from '../factura';
import {ClienteService} from '../../services/cliente.service';
import {ActivatedRoute} from '@angular/router';
import {MatAutocompleteModule, MatInputModule} from '@angular/material';
import {FormControl} from '@angular/forms';
import {FacturaService} from '../../services/factura.service';
import {debounceTime} from 'rxjs/operators';


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
    this.buscar.valueChanges.pipe(debounceTime(400)).subscribe(data => {
      this.facturaService.buscarProductos(data).subscribe(response => {
        this.resultados = response;
      });
    });
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
}
