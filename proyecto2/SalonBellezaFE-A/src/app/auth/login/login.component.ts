import { Component, inject, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ValidadorFormService } from '../../services/validador-form.service';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, NgClass],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm !: FormGroup;

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
    } else {
      console.log('Formulario inválido');
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
