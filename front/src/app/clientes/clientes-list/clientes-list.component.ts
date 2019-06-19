import { ClienteService } from '../../services/cliente.service';
import { Component, OnInit } from '@angular/core';
import { Cliente } from '../cliente';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes.component.css']
})

export class ClientesListComponent implements OnInit {

  isEnabled = true;

  clientes: Cliente[];

  constructor(private clienteService: ClienteService) { }

  ngOnInit() {
    this.clienteService.getClientes().subscribe(
      clientes => this.clientes = clientes
    );
  }

  ocultar(): void {
    this.isEnabled = !this.isEnabled;
  }

  delete(cliente: Cliente): void {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success mx-1',
        cancelButton: 'btn btn-danger mx-1'
      },
      buttonsStyling: false,
    });

    swalWithBootstrapButtons.fire({
      title: 'Eliminar?',
      text: `¿Eliminar cliente ${cliente.nombre + ' ' + cliente.apellidos}?`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, delete it!',
      cancelButtonText: 'No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.clienteService.delete(cliente).subscribe(
          response => {
            this.clientes = this.clientes.filter(cli => cli !== cliente);
            swalWithBootstrapButtons.fire(
              'Eliminado!',
              `cliente ${cliente.nombre + ' ' + cliente.apellidos} eliminado con éxito`,
              'success'
            );
          }
        );
      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          'Cancelado',
          'No se eliminará el cliente',
          'error'
        );
      }
    });
  }
}
