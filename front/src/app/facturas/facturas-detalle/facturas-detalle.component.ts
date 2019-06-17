import {Component, OnInit} from '@angular/core';
import {FacturaService} from '../../services/factura.service';

@Component({
  selector: 'app-facturas-detalle',
  templateUrl: './facturas-detalle.component.html',
  styleUrls: ['./facturas-detalle.component.css']
})
export class FacturasDetalleComponent implements OnInit {

  constructor(private facturaService: FacturaService) {

  }

  ngOnInit() {
  }

}
