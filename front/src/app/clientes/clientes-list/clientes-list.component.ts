import {ClienteService} from '../../services/cliente.service';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {Cliente} from '../../models/cliente';
import {Router} from '@angular/router';
import {SwalService} from '../../services/swal.service';
import {AuthService} from '../../services/auth.service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes.component.css']
})

export class ClientesListComponent implements OnInit, OnDestroy {

  isEnabled = true;
  clientes: Cliente[];
  swalWithBootstrapButtons;
  destroySubject$: Subject<void> = new Subject();

  constructor(private clienteService: ClienteService,
              private swalService: SwalService,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
    this.clienteService.getClientes().pipe(takeUntil(this.destroySubject$)).subscribe(
      clientes => this.clientes = clientes
    );
    this.swalWithBootstrapButtons = this.swalService.getCustomButton();
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
        this.clienteService.delete(cliente).pipe(takeUntil(this.destroySubject$)).subscribe(
          () => {
            this.clientes = this.clientes.filter(cli => cli !== cliente);
            this.swalWithBootstrapButtons.fire(
              'Eliminado!',
              `cliente ${cliente.nombre + ' ' + cliente.apellidos} eliminado con éxito`,
              'success'
            );
          }
        );
      } else if (
        result.dismiss === this.swalService.swal().DismissReason.cancel
      ) {
        this.swalWithBootstrapButtons.fire(
          'Cancelado',
          'No se eliminará el cliente',
          'info'
        );
      }
    });
  }

  exportXml() {
    return this.clienteService.getXml().pipe(takeUntil(this.destroySubject$)).subscribe(response => {
        const blob = new Blob([response], {type: 'text/xml'});
        const url = URL.createObjectURL(blob);
        window.open(url);
        URL.revokeObjectURL(url);
      }
    );
  }

  export() {
    return;
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  ngOnDestroy(): void {
    this.destroySubject$.next();
  }
}
