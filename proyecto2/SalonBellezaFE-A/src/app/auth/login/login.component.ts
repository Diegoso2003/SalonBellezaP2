import { Component, inject, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgClass } from '@angular/common';
import { InformacionComponent } from "../../informacion/informacion.component";
import { Informacion } from '../../models/informacion';
import { Validador } from '../../class/validador-form';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, NgClass, InformacionComponent, InformacionComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm: FormGroup;
  informacion: Informacion;

  private _validadorForm!: Validador;
  private _usuarioServicio = inject(UsuarioService);
  private usuario!: Usuario;

  constructor(private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      correo: ['', [Validators.required, Validators.email]],
      contraseña: ['', [Validators.required]]
    });
    this._validadorForm = new Validador(this.loginForm);
    this.informacion = new Informacion();
  }

  enviar() {
    if (this.loginForm.valid) {
      this.usuario = this.loginForm.value as Usuario;
      console.log(this.usuario);  
      this._usuarioServicio.login(this.usuario).subscribe(
        {
          next: (usuario: Usuario) => {
            console.log(usuario);
            this._usuarioServicio.redireccionarUsuario(usuario);
          },
          error: (error) => {
            console.log(error);
            this.informacion.informarError(error.error.mensaje || 'Error al iniciar sesión');
            this.loginForm.markAllAsTouched();
          }
        }
      );
    } else {
      console.log('Formulario inválido');
      this.informacion.informarError('Formulario inválido');
      this.loginForm.markAllAsTouched();
    }
  }

  faltaEmail(){
    return this._validadorForm.hasErrors('correo', 'required');
  }

  esEmailInvalido(){
    return this._validadorForm.hasErrors('correo', 'email');
  }

  faltaPassword(){
    return this._validadorForm.hasErrors('contraseña', 'required');
  }

  esEmailValido(){
    return this._validadorForm.esValido('correo');
  }

  esPasswordValido(){
    return this._validadorForm.esValido('contraseña');
  }
}
