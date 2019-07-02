import {Component, OnInit} from '@angular/core';
import {ClienteService} from '../../services/cliente.service';
import {Cliente} from '../cliente';
import {ActivatedRoute} from '@angular/router';
import {Factura} from '../../facturas/factura';
import {FacturaService} from '../../services/factura.service';
import {SwalService} from '../../services/swal.service';

@Component({
  selector: 'app-clientes-detalle',
  templateUrl: './clientes-detalle.component.html'
})
export class ClientesDetalleComponent implements OnInit {

  public cliente: Cliente;
  public facturas: Factura[];

  constructor(private clienteService: ClienteService,
              private facturaService: FacturaService,
              private swal: SwalService,
              private rutaActiva: ActivatedRoute) {
  }

  ngOnInit() {
    this.cargarDetalles();
  }

  cargarDetalles() {
    this.rutaActiva.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.clienteService.getCliente(id).subscribe(cliente => {
          this.cliente = cliente;
        });
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
      type: 'warning'
    }).then(result => {
      if (result.value) {
        this.facturaService.delete(factura).subscribe(
          response => {
            console.log(response);
            alert.fire('', 'Factura eliminada', 'info').then();
          },
          err => {
            console.log(err);
          }
        );
      }
    });
  }

}
