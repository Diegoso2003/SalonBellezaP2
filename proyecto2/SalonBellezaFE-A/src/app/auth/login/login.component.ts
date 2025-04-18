import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgClass } from '@angular/common';
import { InformacionComponent } from "../../informacion/informacion.component";
import { Validador } from '../../class/validador-form';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario';
import { InformacionService } from '../../services/informacion.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, NgClass, InformacionComponent, InformacionComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm: FormGroup;
  private _informacion = inject(InformacionService);

  private _validadorForm!: Validador;
  private _usuarioServicio = inject(UsuarioService);
  private usuario!: Usuario;

  constructor(private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      correo: ['', [Validators.required, Validators.email]],
      contraseña: ['', [Validators.required]]
    });
    this._validadorForm = new Validador(this.loginForm);
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
            this._informacion.informarError(error.error.mensaje || 'Error al iniciar sesión');
            this.loginForm.markAllAsTouched();
          }
        }
      );
    } else {
      console.log('Formulario inválido');
      this._informacion.informarError('Formulario inválido');
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
