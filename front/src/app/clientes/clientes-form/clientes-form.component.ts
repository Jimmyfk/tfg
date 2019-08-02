import {ClienteService} from '../../services/cliente.service';
import {Cliente} from '../../models/cliente';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SwalService} from '../../services/swal.service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: []
})
export class ClientesFormComponent implements OnInit, OnDestroy {

  public cliente = new Cliente();
  public titulo = 'Nuevo cliente';
  public errores: string[];
  public destroySubject$: Subject<void> = new Subject();

  constructor(private clienteService: ClienteService,
              private swal: SwalService,
              private router: Router,
              private rutaActiva: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.cargarCliente();
  }

  cargarCliente(): void {
    this.rutaActiva.params.pipe(takeUntil(this.destroySubject$)).subscribe(params => {
      const id = params.id;
      if (id) {
        this.titulo = 'Editar cliente';
        this.clienteService.getCliente(id).subscribe(cliente => this.cliente = cliente);
      }
    });
  }

  public create(): void {
    this.clienteService.create(this.cliente).subscribe(
      response => {
        this.router.navigate(['/clientes']).then(() => this.swal.fire('Nuevo cliente', this.decode(response.mensaje), 'success'));
      },
      response => {
        this.errores = response.error.errores as string[];
        console.error(this.errores);
        console.error('Código de error: ' + response.status);
      }
    );
  }

  public update(): void {
    this.clienteService.update(this.cliente).subscribe(
      response => {
        this.router.navigate(['/clientes']).then(() => this.swal.fire('Cliente actualizado', this.decode(response.mensaje), 'success'));
      },
      response => {
        this.errores = response.error.errores as string[];
        console.error(this.errores);
        console.error('Código de error: ' + response.status);
      }
    );
  }

  private decode(cadena: string): string {
    return decodeURIComponent(escape(cadena));
  }

  test(val) {
    console.log('test');
    console.log(val);
  }

  ngOnDestroy(): void {
    this.destroySubject$.next();
  }
}
