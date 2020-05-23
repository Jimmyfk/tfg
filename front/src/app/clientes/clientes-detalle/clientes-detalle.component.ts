import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {ClienteService} from '../../services/cliente.service';
import {Cliente} from '../../models/cliente';
import {ActivatedRoute} from '@angular/router';
import {Factura} from '../../models/factura';
import {FacturaService} from '../../services/factura.service';
import {SwalService} from '../../services/swal.service';
import {AuthService} from '../../services/auth.service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {LazyloaderService} from '../../services/lazy/lazyloader.service';

@Component({
  selector: 'app-clientes-detalle',
  templateUrl: './clientes-detalle.component.html'
})
export class ClientesDetalleComponent implements OnInit, OnDestroy, AfterViewInit {

  public cliente: Cliente;
  public facturas: Factura[];
  @ViewChild('botonAtras', {read: ViewContainerRef, static: false})
  botonAtras: ViewContainerRef;
  destroySub$: Subject<void> = new Subject();
  titulo: string;
  noFactura: string;

  constructor(private clienteService: ClienteService,
              private facturaService: FacturaService,
              private swal: SwalService,
              private authService: AuthService,
              private loader: LazyloaderService,
              private rutaActiva: ActivatedRoute) {
  }

  ngOnInit() {
    this.rutaActiva.data.pipe(takeUntil(this.destroySub$)).subscribe((data: { cliente: Cliente }) => {
      this.cliente = data.cliente;
    });
    this.getFacturas();
    this.titulo = this.isCliente() ? 'Mi cuenta' : 'Detalles del cliente';
    this.noFactura = this.isCliente() ? 'No tienes ninguna factura'
                                      : `El cliente ${this.cliente.nombre} ${this.cliente.apellidos} no tiene facturas`;
  }

  getFacturas() {
    this.rutaActiva.params.pipe(takeUntil(this.destroySub$)).subscribe(params => {
      const id = params.clienteId;
      if (id) {
        this.facturaService.getFacturas(id).subscribe(facturas => {
          this.facturas = facturas;
        });
      }
    });
  }

  delete(factura: Factura) {
    const alert = this.swal.getCustomButton();
    alert.fire({
      title: 'Confirmar',
      text: '¿Eliminar Factura?',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'No',
      type: 'warning'
    }).then(result => {
      if (result.value) {
        this.facturaService.delete(factura).subscribe(
          response => {
            this.facturas = this.facturas.filter(fact => fact !== factura);
            alert.fire('', 'Factura eliminada', 'info').then();
          },
          err => {
          }
        );
      }
    });
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  isCliente() {
    return this.authService.hasRole('CLIENTE');
  }

  ngOnDestroy(): void {
    this.destroySub$.next();
  }

  tracker(item) {
    return item.id;
  }

  ngAfterViewInit(): void {
    if (!this.authService.hasRole('CLIENTE')) {
      this.loader.load('back-btn', this.botonAtras).then();
    }
  }
}
