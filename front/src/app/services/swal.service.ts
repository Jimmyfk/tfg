import {Injectable} from '@angular/core';
import Swal, {SweetAlertOptions, SweetAlertType} from 'sweetalert2';

const CUSTOM_BUTTONS = {
  customClass: {
    confirmButton: 'btn btn-success btn-sm mx-1',
    cancelButton: 'btn btn-danger btn-sm mx-1'
  },
  buttonsStyling: false,
};

@Injectable({
  providedIn: 'root'
})
export class SwalService {

  constructor() {

  }

  mix(options: SweetAlertOptions) {
    return Swal.mixin(options);
  }

  fire(title: string, msg: string, type: SweetAlertType) {
    return Swal.fire(title, msg, type);
  }

  fireWithOptions(options: SweetAlertOptions) {
    return Swal.fire(options);
  }

  swal() {
    return Swal;
  }

  getCustomButton() {
    return this.mix(CUSTOM_BUTTONS);
  }
}
