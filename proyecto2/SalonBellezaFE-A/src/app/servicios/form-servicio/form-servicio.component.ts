import { Component } from '@angular/core';
import { Informacion } from '../../models/informacion';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InformacionComponent } from "../../informacion/informacion.component";
import { Validador } from '../../class/validador-form';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-form-servicio',
  standalone: true,
  imports: [InformacionComponent, ReactiveFormsModule, NgClass],
  templateUrl: './form-servicio.component.html',
  styleUrl: './form-servicio.component.scss'
})
export class FormServicioComponent {

  informacion: Informacion;
  servicioForm: FormGroup;
  private validadorForm: Validador;
  private foto: File | null = null;
  private inputFotoTocado: boolean = false;
  private catalogo: File | null = null;
  private inputCatalogoTocado: boolean = false;

  constructor(private formBuilder: FormBuilder) {
    this.servicioForm = this.formBuilder.group(
      {
        nombreServicio: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(200)]],
        descripcion: ['', [Validators.required, Validators.minLength(20)]],
        precio: ['', [Validators.required, Validators.min(0.01)]],
        horas: ['', [Validators.min(1), Validators.max(4)]],
        minutos: ['', [Validators.required, Validators.min(1), Validators.max(59)]]
      }
    );
    this.informacion = new Informacion();
    this.validadorForm = new Validador(this.servicioForm);
  }

  enviar() {
    console.log(this.servicioForm.value);
    if(this.servicioForm.get('horas')?.value === ''){

    }
  }

  fotoSeleccionada(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.foto = fileInput.files[0];
    } else {
      this.foto = null;
    }
  }

  catalogoSeleccionado(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.catalogo = fileInput.files[0];
    } else {
      this.catalogo = null;
    }
  }

  fotoTocada() {
    this.inputFotoTocado = true;
  }

  catalogoTocado() {
    this.inputCatalogoTocado = true;
  }

  faltaFoto() {
    return this.inputFotoTocado && this.foto === null;
  }

  esFotoValida() {
    return this.inputFotoTocado && this.foto !== null;
  }

  faltaCatalogo() {
    return this.inputCatalogoTocado && this.catalogo === null;
  }

  esCatalogoValido() {
    return this.inputCatalogoTocado && this.catalogo !== null;
  }

  faltaNombreServicio(){
    return this.validadorForm.hasErrors('nombreServicio', 'required');
  }

  esNombreServicioInvalido() {
    return this.validadorForm.hasErrors('nombreServicio', 'minlength') || this.validadorForm.hasErrors('nombreServicio', 'maxlength');
  }

  esNombreServicioValido() {
    return this.validadorForm.esValido('nombreServicio');
  }

  faltaDescripcion() {
    return this.validadorForm.hasErrors('descripcion', 'required');
  }

  esDescripcionInvalida() {
    return this.validadorForm.hasErrors('descripcion', 'minlength');
  }

  esDescripcionValida() {
    return this.validadorForm.esValido('descripcion');
  }

  faltaPrecio() {
    return this.validadorForm.hasErrors('precio', 'required');
  }

  esPrecioInvalido() {
    return this.validadorForm.hasErrors('precio', 'min');
  }

  esPrecioValido() {
    return this.validadorForm.esValido('precio');
  }

  esHoraInvalida(){
    return this.validadorForm.hasErrors('horas', 'min' ) || this.validadorForm.hasErrors('horas', 'max');
  }

  esHoraValida() {
    return this.validadorForm.esValido('horas');
  }

  faltaMinutos() {
    return this.validadorForm.hasErrors('minutos', 'required');
  }

  esMinutosInvalido() {
    return this.validadorForm.hasErrors('minutos', 'min') || this.validadorForm.hasErrors('minutos', 'max');
  }

  esMinutosValido(){
    return this.validadorForm.esValido('minutos');
  }
}
