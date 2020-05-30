import {ClienteService} from '../../services/cliente.service';
import {Cliente} from '../../models/cliente';
import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SwalService} from '../../services/swal.service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {LazyloaderService} from '../../services/lazy/lazyloader.service';
import {AuthService} from '../../services/auth.service';
import {Usuario} from '../../models/usuario';


@Component({
  selector: 'app-form',
  templateUrl: './clientes-form.component.html',
  styleUrls: []
})
export class ClientesFormComponent implements OnInit, OnDestroy, AfterViewInit {

  public cliente = new Cliente();
  public titulo = 'Nuevo cliente';
  public errores: string[];
  public destroySubject$: Subject<void> = new Subject();
  @ViewChild('botonAtras', {read: ViewContainerRef, static: false})
  botonAtras: ViewContainerRef;
  pw: string;
  pw2: string;
  modificarPw: boolean = false;
  isCliente: boolean;
  usuarioId: number;

  constructor(private clienteService: ClienteService,
              private swal: SwalService,
              private router: Router,
              private rutaActiva: ActivatedRoute,
              private authService: AuthService,
              private loader: LazyloaderService) {
  }

  ngOnInit(): void {
    this.cargarCliente();
    this.isCliente = this.authService.hasRole('CLIENTE');
  }

  cargarCliente(): void {
    this.rutaActiva.params.pipe(takeUntil(this.destroySubject$)).subscribe(params => {
      const id = params.clienteId;
      if (id) {
        this.titulo = 'Editar cliente';
        this.clienteService.getCliente(id).subscribe(cliente => this.cliente = cliente);
        this.clienteService.getUsuario(id).subscribe((response: any) => {
          this.pw = response.usuario.password;
          this.pw2 = response.usuario.password;
          this.usuarioId = response.usuario.id;
        });
      }
    });
  }

  public create(): void {
    if (this.validar()) {
      this.clienteService.create(this.cliente, this.pw).subscribe(
        response => {
          this.router.navigate(['/clientes']).then(() => this.swal.fire('Nuevo cliente', response.mensaje, 'success'));
        },
        response => {
          this.errores = response.error.errores as string[];
          console.error(this.errores);
          console.error('Código de error: ' + response.status);
        }
      );
    }
  }

  public update(): void {
    if (!this.modificarPw) {
      this.clienteService.update(this.cliente).subscribe(
        response => {
          this.router.navigate([this.isCliente ? '/inicio' : '/clientes'])
            .then(() => this.swal.fire('Cliente actualizado', response.mensaje, 'success'));
        },
        response => {
          this.errores = response.error.errores as string[];
          console.error(this.errores);
          console.error('Código de error: ' + response.status);
        }
      );
    } else {
      this.clienteService.updatePassword(this.usuarioId, this.pw).subscribe((response: any) => {
        this.swal.getCustomButton().fire('Resultado de la modificación', response.mensaje, 'info');
      }, error => {
        this.swal.getCustomButton().fire('Error', error.error.error, 'error');
      });
    }
  }

  test(val) {
    console.log('test', val);
  }

  ngOnDestroy(): void {
    this.destroySubject$.next();
  }

  ngAfterViewInit(): void {
    this.loader.load('back-btn', this.botonAtras).then();
  }

  private validar() {

    if (!this.pw) {
      this.swal.getCustomButton().fire('Error', 'La contraseña es obligatoria', 'error');
      return false;
    }

    if (!this.pw2) {
      this.swal.getCustomButton().fire('Aviso', 'Confirma la contraseña', 'warning');
      return false;
    }

    if (this.pw !== this.pw2) {
      this.swal.getCustomButton().fire('Error', 'Las contraseñas no coinciden', 'error');
      return false;
    }

    return true;
  }

  modidificarClave() {
    this.modificarPw = !this.modificarPw;
  }
}
