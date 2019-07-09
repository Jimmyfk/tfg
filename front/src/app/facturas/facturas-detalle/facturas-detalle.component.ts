import {Component, OnDestroy, OnInit} from '@angular/core';
import {FacturaService} from '../../services/factura.service';
import {ActivatedRoute} from '@angular/router';
import {Factura} from '../../models/factura';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-facturas-detalle',
  templateUrl: './facturas-detalle.component.html',
  styleUrls: []
})
export class FacturasDetalleComponent implements OnInit, OnDestroy {

  public factura: Factura;
  destroySub$: Subject<void> = new Subject();

  constructor(private facturaService: FacturaService,
              private rutaActiva: ActivatedRoute) {

  }

  ngOnInit() {
    this.cargarDetalles();
  }

  cargarDetalles() {
    this.rutaActiva.params.pipe(takeUntil(this.destroySub$)).subscribe(params => {
      const id = params.id;
      if (id) {
        this.facturaService.getDetalleFactura(id).subscribe(factura => {
          this.factura = factura;
        });
      }
    });
  }

  ngOnDestroy(): void {
    this.destroySub$.next();
  }

  tracker(item) {
    return item.id;
  }

}
