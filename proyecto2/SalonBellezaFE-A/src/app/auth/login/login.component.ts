import { Component, inject, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ValidadorFormService } from '../../services/validador-form.service';
import { NgClass } from '@angular/common';
import { InformacionComponent } from "../../informacion/informacion.component";
import { Informacion } from '../../models/informacion';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, NgClass, InformacionComponent, InformacionComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm !: FormGroup;
  informacion: Informacion = {
    hayError: false,
    mensaje: '',
    exito: false,
    mostrarAlertaExito: false
  };

  private _validadorForm = inject(ValidadorFormService);

  constructor(private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      contraseña: ['', [Validators.required]]
    });
    this._validadorForm.initForm(this.loginForm);
  }

  enviar() {
    if (this.loginForm.valid) {
      console.log(this.loginForm.value);
      this.informacion.exito = true;
      this.informacion.hayError = false;
      this.informacion.mensaje = 'Formulario enviado correctamente';
      this.informacion.mostrarAlertaExito = true;
    } else {
      console.log('Formulario inválido');
      this.informacion.hayError = true;
      this.informacion.mensaje = 'Formulario inválido';
      this.informacion.exito = false;
    }
  }

  faltaEmail(){
    return this._validadorForm.hasErrors('email', 'required');
  }

  esEmailInvalido(){
    return this._validadorForm.hasErrors('email', 'email');
  }

  faltaPassword(){
    return this._validadorForm.hasErrors('contraseña', 'required');
  }

  esEmailValido(){
    return this._validadorForm.esValido('email');
  }

  esPasswordValido(){
    return this._validadorForm.esValido('contraseña');
  }
}
