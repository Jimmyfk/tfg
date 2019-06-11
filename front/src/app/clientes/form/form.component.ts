import {ClienteService} from './../cliente.service';
import {Cliente} from './../cliente';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import swal from 'sweetalert2';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: []
})
export class FormComponent implements OnInit {

  constructor(private clienteService: ClienteService,
              private router: Router,
              private rutaActiva: ActivatedRoute) { }

  public cliente = new Cliente();
  public titulo = 'Nuevo cliente';
  public errores: string[];

  ngOnInit(): void {
   this.cargarCliente();
  }

  cargarCliente(): void {
      this.rutaActiva.params.subscribe(params => {
        const id = params.id;
        if (id) {
          this.titulo = 'Editar cliente';
          this.clienteService.getCliente(id).subscribe( (cliente) => this.cliente = cliente);
        }
      });
  }

  public create(): void {
    this.clienteService.create(this.cliente).subscribe(
      response => {
        this.router.navigate(['/clientes']);
        swal.fire('Nuevo cliente', this.decode(response.nombre) + ' creado con éxito', 'success');
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
         this.router.navigate(['/clientes']);
         swal.fire('Cliente actualizado', this.decode(response.nombre) + ' actualizado con éxito', 'success');
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
}
