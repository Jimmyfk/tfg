import {ClienteService} from '../../services/cliente.service';
import {Component, OnInit} from '@angular/core';
import {Cliente} from '../cliente';
import Swal from 'sweetalert2';
import {error} from 'selenium-webdriver';
import {Router} from '@angular/router';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes.component.css']
})

export class ClientesListComponent implements OnInit {

  isEnabled = true;

  clientes: Cliente[];

  swalWithBootstrapButtons;

  constructor(private clienteService: ClienteService,
              private router: Router) {
  }

  ngOnInit() {
    this.clienteService.getClientes().subscribe(
      clientes => this.clientes = clientes
    );
    this.swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success btn-sm mx-1',
        cancelButton: 'btn btn-danger btn-sm mx-1'
      },
      buttonsStyling: false,
    });
  }

  ocultar(): void {
    this.isEnabled = !this.isEnabled;
  }

  delete(cliente: Cliente): void {
    this.swalWithBootstrapButtons.fire({
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
            this.swalWithBootstrapButtons.fire(
              'Eliminado!',
              `cliente ${cliente.nombre + ' ' + cliente.apellidos} eliminado con éxito`,
              'success'
            );
          }
        );
      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
        this.swalWithBootstrapButtons.fire(
          'Cancelado',
          'No se eliminará el cliente',
          'error'
        );
      }
    });
  }
}
