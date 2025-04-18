import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InformacionComponent } from "../../informacion/informacion.component";
import { Validador } from '../../class/validador-form';
import { NgClass } from '@angular/common';
import { FormEmpleadosComponent } from "../form-empleados/form-empleados.component";
import { Usuario } from '../../models/usuario';
import { ServiciosService } from '../../services/servicios/servicios.service';
import { Servicio } from '../../models/servicio';
import { InformacionService } from '../../services/informacion.service';

@Component({
  selector: 'app-form-servicio',
  standalone: true,
  imports: [InformacionComponent, ReactiveFormsModule, NgClass, FormEmpleadosComponent],
  templateUrl: './form-servicio.component.html',
  styleUrl: './form-servicio.component.scss'
})
export class FormServicioComponent implements OnInit{

  private informacion = inject(InformacionService);
  servicioForm: FormGroup;
  private validadorForm: Validador;
  private foto: File | null = null;
  private catalogo: File | null = null;
  empleadosDelServicio: Usuario[] = [];
  empleados!: Usuario[];

  private _servicioService = inject(ServiciosService);

  constructor(private formBuilder: FormBuilder) {
    this.servicioForm = this.formBuilder.group(
      {
        nombreServicio: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(200)]],
        descripcion: ['', [Validators.required, Validators.minLength(20)]],
        precio: ['', [Validators.required, Validators.min(0.01)]],
        horas: ['', [Validators.min(1), Validators.max(4)]],
        minutos: ['', [Validators.required, Validators.min(1), Validators.max(59)]],
        foto: ['', [Validators.required]],
        catalogo: ['', [Validators.required]],
      }
    );
    this.validadorForm = new Validador(this.servicioForm);
  }

  ngOnInit(): void {
    this._servicioService.obtenerEmpleados().subscribe(
      {
        next: (empleados: Usuario[]) => {
          this.empleados = empleados;
        },
        error: (error) => {
          console.error('Error al obtener los empleados:', error);
          this.informacion.informarError('Error al obtener los empleados');
        }
      }
    );
  }

  enviar() {
    console.log(this.servicioForm.value);
    if(this.servicioForm.valid && this.foto !== null && this.catalogo !== null){
      if(this.empleadosDelServicio.length === 0){
        this.informacion.informarError('Agregar al menos un empleado');
        return;
      }
      let horas: string = this.obtenerHoras();
      let minutos: string = this.obtenerMinutos();
      let servicio: Servicio = {
        idServicio: 0,
        nombreServicio: this.servicioForm.value.nombreServicio,
        descripcion: this.servicioForm.value.descripcion,
        precio: this.servicioForm.value.precio,
        duracion: `${horas}:${minutos}`,
        empleados: this.empleadosDelServicio,
        activo: true
      }
      const formData = new FormData();
      formData.append('servicio', JSON.stringify(servicio));
      formData.append('foto', this.foto);
      formData.append('catalogo', this.catalogo);
      this._servicioService.crearServicio(formData).subscribe(
        {
          next: () => {
            this.servicioForm.reset();
            this.foto = null;
            this.catalogo = null;
            this.empleadosDelServicio.length = 0;
            this.informacion.informarExito('Servicio creado con éxito');
          },
          error: (error) => {
            console.error('Error al crear el servicio:', error);
            this.informacion.informarError(error.error.mensaje || 'Error al crear el servicio');
          }
        }
      );
    } else{
      this.servicioForm.markAllAsTouched();
      this.servicioForm.value;
      this.informacion.informarError('Completar los campos requeridos')
    }
  }

  private obtenerHoras(): string {
    let horas: string = this.servicioForm.value.horas.toString();
    if(horas.length === 0){
      horas = '00';
    } else if(horas.length === 1){
      horas = '0' + horas;
    }
    return horas;
  }

  private obtenerMinutos(): string {
    let minutos: string = this.servicioForm.value.minutos.toString();
    if(minutos.length === 1){
      minutos = '0' + minutos;
    }
    return minutos;
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

  faltaFoto() {
    return this.validadorForm.hasErrors('foto', 'required') && this.foto === null;
  }

  esFotoValida() {
    return this.validadorForm.esValido('foto') && this.foto !== null;
  }

  faltaCatalogo() {
    return this.validadorForm.hasErrors('catalogo', 'required') && this.catalogo === null;
  }

  esCatalogoValido() {
    return this.validadorForm.esValido('catalogo') && this.catalogo !== null;
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
