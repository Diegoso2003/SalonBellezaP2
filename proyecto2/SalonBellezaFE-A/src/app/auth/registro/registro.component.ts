import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ValidadorFormService } from '../../services/validador-form.service';
import { NgClass } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Informacion } from '../../models/informacion';
import { InformacionComponent } from '../../informacion/informacion.component';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, RouterLink, InformacionComponent],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.scss'
})
export class RegistroComponent {
  registroForm !: FormGroup;
  private _validadorForm = inject(ValidadorFormService);
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
      direccion: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      contraseña: ['', [Validators.required, Validators.minLength(6)]],
      confirmarContraseña: ['', [Validators.required]]
    })
    this._validadorForm.initForm(this.registroForm);
  }

  enviar() {
    if (this.registroForm.valid) {
      console.log(this.registroForm.value);
      this.informacion.exito = true;
      this.informacion.hayError = false;
      this.informacion.mensaje = 'Formulario enviado correctamente';
      this.informacion.mostrarAlertaExito = true;
    } else {
      this.informacion.hayError = true;
      this.informacion.mensaje = 'Formulario inválido';
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

  faltaDireccion(){
    return this._validadorForm.hasErrors('direccion', 'required');
  }

  esDireccionInvalida() {
    return this._validadorForm.hasErrors('direccion', 'minlength');
  }

  esDireccionValida() {
    return this._validadorForm.esValido('direccion');
  }

  faltaEmail() {
    return this._validadorForm.hasErrors('email', 'required');
  }

  esEmailInvalido() {
    return this._validadorForm.hasErrors('email', 'email');
  }

  esEmailValido() {
    return this._validadorForm.esValido('email');
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
    return this._validadorForm.hasErrors('confirmarContraseña', 'minlength');
  }

  faltaConfirmacionPassword() {
    return this._validadorForm.hasErrors('confirmarContraseña', 'required');
  }

  esConfirmacionPasswordValido() {
    return this._validadorForm.esValido('confirmarContraseña');
  }

  esPasswordIgual() {
    return this.registroForm.get('contraseña')?.value === this.registroForm.get('confirmarContraseña')?.value;
  }

  esPasswordIgualValido() {
    return this.esPasswordIgual() &&  this.esConfirmacionPasswordValido() &&this.esPasswordValido();
  }

  esPasswordIgualInvalido() {
    return !this.esPasswordIgual() && this.esConfirmacionPasswordValido() &&this.esPasswordValido();
  }
}
