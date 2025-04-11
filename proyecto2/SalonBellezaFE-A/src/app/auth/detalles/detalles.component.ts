import { NgClass } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InformacionComponent } from '../../informacion/informacion.component';
import { Informacion } from '../../models/informacion';
import { Validador } from '../../class/validador-form';
import { ClienteService } from '../../services/cliente/cliente.service';
import { Usuario } from '../../models/usuario';
import { UsuarioService } from '../../services/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-detalles',
  standalone: true,
  imports: [NgClass, ReactiveFormsModule, InformacionComponent],
  templateUrl: './detalles.component.html',
  styleUrl: './detalles.component.scss'
})
export class DetallesComponent {

  detallesForm: FormGroup;
  informacion: Informacion;

  private validadorform!: Validador
  private _clienteService = inject(ClienteService);
  private _usuarioService = inject(UsuarioService);
  private router = inject(Router);
  private usuario!: Usuario;
  private foto: File | null = null;
  inputFotoTocado: boolean = false;

  constructor(private formBuilder: FormBuilder){
    this.detallesForm = this.formBuilder.group(
      {
        direccion: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(250)]],
        descripcion: ['', [Validators.required, Validators.minLength(20)]],
        hobbies: ['', [Validators.required, Validators.minLength(20)]],
        gustos: ['', [Validators.required, Validators.minLength(20)]]
      }
    );
    this.validadorform = new Validador(this.detallesForm);
    this.informacion = new Informacion();
  }

  enviar() {
    if(this.detallesForm.valid && this.foto !== null){
      this.usuario = this.detallesForm.value as Usuario;
      this.usuario.dpi = this._usuarioService.getToken() || '1';
      console.log(this.usuario);
      const formData = new FormData();
      formData.append('datos', JSON.stringify(this.usuario));
      formData.append('foto', this.foto);
      this._clienteService.añadirDetalles(formData).subscribe({
        next: () => {
          this.router.navigate(['/cliente']);
        },
        error: (error) => {
          console.log(error);
          this.informacion.informarError(error.error.mensaje || 'Error al añadir detalles'); 
          this.detallesForm.markAllAsTouched();
        }
      });
    } else {
      this.informacion.informarError('Ingresa todos los campos');
      this.detallesForm.markAllAsTouched();
      this.inputFotoTocado = true;
    }
  }


  archivoSeleccionado(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.foto = fileInput.files[0];
    } else {
      this.foto = null;
    }
    this.inputFotoTocado = true;
  }

  faltaDescripcion() {
    return this.validadorform.hasErrors('descripcion', 'required');
  }

  esDescripcionInvalida() {
    return this.validadorform.hasErrors('descripcion', 'minlength');
  }

  esDescripcionValida() {
    return this.validadorform.esValido('descripcion');
  }

  faltaGustos() {
    return this.validadorform.hasErrors('gustos', 'required');
  }

  esGustosInvalido() {
    return this.validadorform.hasErrors('gustos', 'minlength');
  }

  esGustosValido() {
    return this.validadorform.esValido('gustos');
  }

  faltaHobbies() {
    return this.validadorform.hasErrors('hobbies', 'required');
  }

  sonHobbiesInvalido() {
    return this.validadorform.hasErrors('hobbies', 'minlength');
  }

  sonHobbiesValido() {
    return this.validadorform.esValido('hobbies');
  }

  faltaDireccion() {
    return this.validadorform.hasErrors('direccion', 'required');
  }

  esDireccionInvalida() {
    return this.validadorform.hasErrors('direccion', 'minlength') || this.validadorform.hasErrors('direccion', 'maxlength');
  }

  esDireccionValida() {
    return this.validadorform.esValido('direccion');
  }

  faltaFoto() {
    return this.foto === null && this.inputFotoTocado;
  }

  esFotoValida() {
    return this.foto !== null && this.inputFotoTocado;
  }
}
