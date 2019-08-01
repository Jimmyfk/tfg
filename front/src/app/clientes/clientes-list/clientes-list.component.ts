import {ClienteService} from '../../services/cliente.service';
import {Component, OnInit} from '@angular/core';
import {Cliente} from '../../models/cliente';
import {Router} from '@angular/router';
import {SwalService} from '../../services/swal.service';
import {AuthService} from '../../services/auth.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes.component.css'],
})

export class ClientesListComponent implements OnInit {

  isEnabled = true;
  clientes: Cliente[];
  jsonHref: SafeUrl = '';

  constructor(private clienteService: ClienteService,
              private swalService: SwalService,
              private authService: AuthService,
              private modalService: NgbModal,
              private sanitizer: DomSanitizer,
              private router: Router) {
  }

  ngOnInit() {
    this.clienteService.getClientes().subscribe(
      clientes => this.clientes = clientes.clientes
    );
  }

  ocultar(): void {
    this.isEnabled = !this.isEnabled;
  }

  delete(cliente: Cliente): void {
    this.swalService.getCustomButton().fire({
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
          () => {
            this.clientes = this.clientes.filter(cli => cli !== cliente);
            this.swalService.getCustomButton().fire(
              'Eliminado!',
              `cliente ${cliente.nombre + ' ' + cliente.apellidos} eliminado con éxito`,
              'success'
            );
          }
        );
      } else if (
        result.dismiss === this.swalService.swal().DismissReason.cancel
      ) {
        this.swalService.getCustomButton().fire(
          'Cancelado',
          'No se eliminará el cliente',
          'info'
        );
      }
    });
  }

  exportXml() {
    return this.clienteService.getXml().subscribe(response => {
        const blob = new Blob([response], {type: 'text/xml'});
        const url = URL.createObjectURL(blob);
        window.open(url);
        URL.revokeObjectURL(url);
      }
    );
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then();
    this.prepareDownload();
  }

  private prepareDownload() {
    const json = JSON.stringify(this.clientes, null, '\t');
    console.log(this.clientes);
    console.log(json);
    const blob = new Blob([json], {type: 'application/json'});
    const url = URL.createObjectURL(blob);
    this.jsonHref = this.sanitizer.bypassSecurityTrustUrl(url);
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  tracker(item) {
    return item.id;
  }
}
