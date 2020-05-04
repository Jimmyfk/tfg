import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {FacturaService} from '../../services/factura.service';
import {ActivatedRoute} from '@angular/router';
import {Factura} from '../../models/factura';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {LazyloaderService} from '../../services/lazy/lazyloader.service';

@Component({
  selector: 'app-facturas-detalle',
  templateUrl: './facturas-detalle.component.html',
  styleUrls: []
})
export class FacturasDetalleComponent implements OnInit, OnDestroy, AfterViewInit {

  public factura: Factura;
  @ViewChild('botonAtras', {read: ViewContainerRef, static: false})
  botonAtras: ViewContainerRef;
  destroySub$: Subject<void> = new Subject();

  constructor(private facturaService: FacturaService,
              private loader: LazyloaderService,
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
          this.factura = Object.assign(new Factura(), factura);
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

  exportarPdf() {
    this.facturaService.getPdf(this.factura.id).subscribe((response) => {
      console.log(response);
    }, error => {
      console.log(error, 'error');
    });
  }

  ngAfterViewInit(): void {
    this.loader.load('back-btn', this.botonAtras).then();
  }

}
