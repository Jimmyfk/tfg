import {Component, OnDestroy, OnInit} from '@angular/core';
import {ClienteService} from '../../services/cliente.service';
import {Cliente} from '../../models/cliente';
import {ActivatedRoute} from '@angular/router';
import {Factura} from '../../models/factura';
import {FacturaService} from '../../services/factura.service';
import {SwalService} from '../../services/swal.service';
import {AuthService} from '../../services/auth.service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-clientes-detalle',
  templateUrl: './clientes-detalle.component.html'
})
export class ClientesDetalleComponent implements OnInit, OnDestroy {

  public cliente: Cliente;
  public facturas: Factura[];
  destroySub$: Subject<void> = new Subject();

  constructor(private clienteService: ClienteService,
              private facturaService: FacturaService,
              private swal: SwalService,
              private authService: AuthService,
              private rutaActiva: ActivatedRoute) {
  }

  ngOnInit() {
    this.rutaActiva.data.pipe(takeUntil(this.destroySub$)).subscribe((data: { cliente: Cliente }) => {
      this.cliente = data.cliente;
    });
    this.getFacturas();
  }

  getFacturas() {
    this.rutaActiva.params.pipe(takeUntil(this.destroySub$)).subscribe(params => {
      const id = params.id;
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
            console.log(response);
            this.facturas = this.facturas.filter(fact => fact !== factura);
            alert.fire('', 'Factura eliminada', 'info').then();
          },
          err => {
            console.log(err);
          }
        );
      }
    });
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  ngOnDestroy(): void {
    this.destroySub$.next();
  }

  tracker(item) {
    return item.id;
  }
}
