import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgClass } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
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

export class RegistroComponent implements OnInit{
  registroForm: FormGroup;
  private _validadorForm: Validador;
  private _usuarioServicio = inject(UsuarioService)
  private route = inject(ActivatedRoute);
  private usuario!: Usuario;
  private foto: File | null = null;
  inputFotoTocado: boolean = false;
  cliente!: boolean;
  empleado: boolean = false;

  ngOnInit() {
    this.cliente = this.route.snapshot.data['cliente'] ?? true;
  }

  informacion: Informacion;

  constructor(private formBuilder: FormBuilder) {
    this.registroForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(6)]],
      dpi: ['', [Validators.required, Validators.pattern(/^[1-9]\d{12}$/)]],
      telefono: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
      correo: ['', [Validators.required, Validators.email]],
      contraseña: ['', [Validators.required, Validators.minLength(6)]],
      confirmacionContraseña: ['', [Validators.required]]
    })
    if (!this.cliente) {
      this.registroForm.addControl('rol', this.formBuilder.control('', [Validators.required]));
    }
    this._validadorForm = new Validador(this.registroForm);
    this.informacion = new Informacion();
  }

  enviar() {
    if(this.cliente){
      this.enviarCliente();
    } else {
      this.enviarEmpleado();
    }
  }

  faltaRol() {
    return this._validadorForm.hasErrors('rol', 'required');
  }

  esRolValido(){
    return this._validadorForm.esValido('rol');
  }

  private enviarEmpleado() {
    if(this.registroForm.valid && this.esPasswordIgualValido()){
      this.usuario = this.registroForm.value as Usuario;
      if(this.empleado){
        if(this.foto === null){
          this.informacion.informarError('ingresar correctamente los datos solicitados');
          this.inputFotoTocado = true;
          this.registroForm.markAllAsTouched();
          return;
        }
        const formData = new FormData();
        formData.append('detalles', JSON.stringify(this.usuario));
        formData.append('foto', this.foto);
        this._usuarioServicio.registrarEmpleado(formData).subscribe(
          {
            next: () => {
              this.registroForm.reset();
              this.foto = null;
              this.inputFotoTocado = false;
              this.empleado = false;
              if (this.registroForm.contains('descripcion')) {
                this.registroForm.removeControl('descripcion');
              }
            },
            error: (error) => {
              console.log(error);
              this.informacion.informarError(error.error.mensaje || 'Error al registrar el usuario');
              this.registroForm.markAllAsTouched();
            }
          }
        );
        return;
      }
      this._usuarioServicio.registrarUsuario(this.usuario).subscribe(
        {
          next: () => {
            this.registroForm.reset();
            this.foto = null;
            this.inputFotoTocado = false;
          },
          error: (error) => {
            console.log(error);
            this.informacion.informarError(error.error.mensaje || 'Error al registrar el usuario');
            this.registroForm.markAllAsTouched();
            this.inputFotoTocado = true;            
          }
        }
      );
    } else {
      this.informacion.informarError('Ingrese todos los datos correctamente');
      this.registroForm.markAllAsTouched();
      console.log('Formulario inválido');
      if(this.empleado){
        this.inputFotoTocado = true;
      }
    }
  }

  private enviarCliente() {
    if (this.registroForm.valid && this.esPasswordIgualValido()) {
      console.log(this.registroForm.value);
      this.usuario = this.registroForm.value as Usuario;
      this.usuario.rol = Rol.CLIENTE;
      this._usuarioServicio.registrarUsuario(this.usuario).subscribe({
        next: () => {
          this.usuario.activo = false;
          this._usuarioServicio.redireccionarUsuario(this.usuario);
        },
        error: (error) => {
          console.log(error);
          this.informacion.informarError(error.error.mensaje || 'Error al registrar el usuario');
          this.registroForm.markAllAsTouched();
        }
      });
    } else {
      this.informacion.informarError('Ingrese todos los datos correctamente');
      this.registroForm.markAllAsTouched();
      console.log('Formulario inválido');
    }
  }

  archivoSeleccionado(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.foto = fileInput.files[0];
    } else {
      this.foto = null;
    }
  }

  rolSeleccionado(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const selectedValue = selectElement.value;

    if (selectedValue === Rol.EMPLEADO) {
      this.empleado = true;
      if (!this.registroForm.contains('descripcion')) {
        this.registroForm.addControl('descripcion', this.formBuilder.control('', [Validators.required, Validators.minLength(20)]));
      }
    } else {
      this.empleado = false;
      this.inputFotoTocado = false;
      this.foto = null;
      if (this.registroForm.contains('descripcion')) {
        this.registroForm.removeControl('descripcion');
      }
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

  faltaFoto() {
    return this.foto === null && this.inputFotoTocado;
  }

  esFotoValida() {
    return this.foto !== null && this.inputFotoTocado;
  }

  faltaDescripcion() {
    return this._validadorForm.hasErrors('descripcion', 'required');
  }

  esDescripcionInvalida() {
    return this._validadorForm.hasErrors('descripcion', 'minlength');
  }

  esDescripcionValida() {
    return this._validadorForm.esValido('descripcion');
  }
}
