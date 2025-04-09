import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgClass } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Informacion } from '../../models/informacion';
import { InformacionComponent } from '../../informacion/informacion.component';
import { Validador } from '../../class/validador-form';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario';
import { Rol } from '../../models/enums/Rol';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, RouterLink, InformacionComponent],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.scss'
})
export class RegistroComponent {
  registroForm !: FormGroup;
  private _validadorForm!: Validador;
  private _usuarioServicio = inject(UsuarioService)
  private usuario!: Usuario;

  informacion: Informacion = {
    hayError: false,
    mensaje: '',
    exito: false,
    mostrarAlertaExito: false
  };

  constructor(private formBuilder: FormBuilder) {
    this.registroForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(6)]],
      dpi: ['', [Validators.required, Validators.pattern(/^[1-9]\d{12}$/)]],
      telefono: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
      correo: ['', [Validators.required, Validators.email]],
      contraseña: ['', [Validators.required, Validators.minLength(6)]],
      confirmacionContraseña: ['', [Validators.required]]
    })
    this._validadorForm = new Validador(this.registroForm);
  }

  enviar() {
    if (this.registroForm.valid && this.esPasswordIgualValido()) {
      console.log(this.registroForm.value);
      this.usuario = this.registroForm.value as Usuario;
      this.usuario.rol = Rol.CLIENTE;
      this._usuarioServicio.registrarCliente(this.usuario).subscribe({
        next: () => {
          this.usuario.activo = false;
          this._usuarioServicio.redireccionarUsuario(this.usuario);
        },
        error: (error) => {
          console.log(error);
          this.informacion.hayError = true;
          this.informacion.mensaje = error.error.mensaje || 'Error al registrar el usuario';
          this.informacion.mostrarAlertaExito = false;
          this.registroForm.markAllAsTouched();
          this.informacion.exito = false;
        }
      });
    } else {
      this.informacion.hayError = true;
      this.informacion.mensaje = 'ingresar correctamente los datos';
      this.informacion.mostrarAlertaExito = false;
      this.registroForm.markAllAsTouched();
      this.informacion.exito = false;
      console.log('Formulario inválido');
    }
  }

  faltaNombre() {
    return this._validadorForm.hasErrors('nombre', 'required');
  }

  esNombreInvalido() {
    return this._validadorForm.hasErrors('nombre', 'minlength');
  }

  esNombreValido() {
    return this._validadorForm.esValido('nombre');
  }

  faltaDpi() {
    return this._validadorForm.hasErrors('dpi', 'required');
  }

  esDpiInvalido() {
    return this._validadorForm.hasErrors('dpi', 'pattern');
  }

  esDpiValido() {
    return this._validadorForm.esValido('dpi');
  }

  faltaTelefono(){
    return this._validadorForm.hasErrors('telefono', 'required');
  }

  esTelefonoInvalido(){
    return this._validadorForm.hasErrors('telefono', 'minlength') || this._validadorForm.hasErrors('telefono', 'maxlength');
  }

  esTelefonoValido(){
    return this._validadorForm.esValido('telefono');
  }

  faltaEmail() {
    return this._validadorForm.hasErrors('correo', 'required');
  }

  esEmailInvalido() {
    return this._validadorForm.hasErrors('correo', 'email');
  }

  esEmailValido() {
    return this._validadorForm.esValido('correo');
  }

  esPasswordInvalido() {
    return this._validadorForm.hasErrors('contraseña', 'minlength');
  }

  faltaPassword() {
    return this._validadorForm.hasErrors('contraseña', 'required');
  }

  esPasswordValido() {
    return this._validadorForm.esValido('contraseña');
  }

  esConfirmacionPasswordInvalido() {
    return this._validadorForm.hasErrors('confirmacionContraseña', 'minlength');
  }

  faltaConfirmacionPassword() {
    return this._validadorForm.hasErrors('confirmacionContraseña', 'required');
  }

  esConfirmacionPasswordValido() {
    return this._validadorForm.esValido('confirmacionContraseña');
  }

  esPasswordIgual() {
    return this.registroForm.get('contraseña')?.value === this.registroForm.get('confirmacionContraseña')?.value;
  }

  esPasswordIgualValido() {
    return this.esPasswordIgual() &&  this.esConfirmacionPasswordValido() &&this.esPasswordValido();
  }

  esPasswordIgualInvalido() {
    return !this.esPasswordIgual() && this.esConfirmacionPasswordValido() &&this.esPasswordValido();
  }
}
