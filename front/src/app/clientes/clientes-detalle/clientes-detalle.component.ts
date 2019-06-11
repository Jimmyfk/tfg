import {Component, OnInit} from '@angular/core';
import {ClienteService} from '../cliente.service';
import {Cliente} from '../cliente';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-clientes-detalle',
  templateUrl: './clientes-detalle.component.html',
  styleUrls: ['./clientes-detalle.component.css']
})
export class ClientesDetalleComponent implements OnInit {

  public cliente: Cliente;

  constructor(private clienteService: ClienteService, private rutaActiva: ActivatedRoute) { }

  ngOnInit() {
    this.cargarDetalles();
  }

  cargarDetalles() {
    this.rutaActiva.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.clienteService.getCliente(id).subscribe(cliente => this.cliente = cliente);
      }
    });
  }

}
