import {Component, OnInit} from '@angular/core';
import {FacturaService} from '../../services/factura.service';
import {ActivatedRoute} from '@angular/router';
import {Factura} from '../factura';

@Component({
  selector: 'app-facturas-detalle',
  templateUrl: './facturas-detalle.component.html',
  styleUrls: ['./facturas-detalle.component.css']
})
export class FacturasDetalleComponent implements OnInit {

  public factura: Factura;

  constructor(private facturaService: FacturaService,
              private rutaActiva: ActivatedRoute) {

  }

  ngOnInit() {
    this.cargarDetalles();
  }

  cargarDetalles() {
    this.rutaActiva.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.facturaService.getDetalleFactura(id).subscribe(factura => {
          this.factura = factura;
        });
      }
    });
  }

}
