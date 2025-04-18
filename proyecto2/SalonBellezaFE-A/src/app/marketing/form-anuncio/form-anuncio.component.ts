import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InformacionComponent } from "../../informacion/informacion.component";
import { Informacion } from '../../models/informacion';
import { Validador } from '../../class/validador-form';
import { CurrencyPipe, NgClass } from '@angular/common';
import { TipoAnuncio } from '../../models/enums/tipoAnuncio';
import { MarketingService } from '../../services/marketing/marketing.service';
import { PreciosAnuncio } from '../../models/preciosAnuncio';
import { AnuncioDTO } from '../../DTO/anuncioDTO';
import { MensajeDTO } from '../../DTO/mensajeDTO';

@Component({
  selector: 'app-form-anuncio',
  standalone: true,
  imports: [InformacionComponent, ReactiveFormsModule, NgClass, CurrencyPipe],
  templateUrl: './form-anuncio.component.html',
  styleUrl: './form-anuncio.component.scss'
})

export class FormAnuncioComponent implements OnInit{
  formAnuncio: FormGroup;
  private imagen: File | null = null;
  private validadorForm: Validador;
  private inputFotoTocado: boolean = false;
  informacion: Informacion;
  pedirFoto: boolean = false;
  pedirUrlVideo: boolean = false;
  private precios!: PreciosAnuncio;
  precioFinal: number = 0;
  private dias: number = 0;
  private tipo: number = 0;

  private _marketingService = inject(MarketingService);

  ngOnInit(){
    this.obtenerPrecios();
  }

  private obtenerPrecios(){
    this._marketingService.obtenerDatosAnuncio().subscribe(
      {
        next: (precios: PreciosAnuncio) => {
          this.precios = precios;
        },
        error: (error) => {
          this.informacion.informarError('Error al obtener los precios');
        }
      }
    );
  }

  constructor(private formBuilder: FormBuilder){
    this.formAnuncio = this.formBuilder.group(
      {
        tipo: ['', [Validators.required]],
        texto: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(150)]],
        fechaPublicacion: ['', [Validators.required]],
        dias: ['', [Validators.required, Validators.min(1)]]
      }
    );
    this.informacion = new Informacion();
    this.validadorForm = new Validador(this.formAnuncio);
  }

  enviar() {
    if(this.formAnuncio.valid){
      let anuncio: AnuncioDTO = this.formAnuncio.value as AnuncioDTO;
      if(anuncio.tipo === TipoAnuncio.IMAGEN){
        this.cargarAnuncioImagen(anuncio);
      } else {
        this.cargarAnuncio(anuncio);
      }
      this.obtenerPrecios();
    } else {
      this.informacion.informarError('Ingresar los campos requeridos');
      this.formAnuncio.markAllAsTouched();
      this.inputFotoTocado = true;
    }
  }

  cargarAnuncioImagen(anuncio: AnuncioDTO) {
    let formData: FormData = new FormData();
    formData.append('datos', JSON.stringify(anuncio));
    if(this.imagen !== null){
      formData.append('imagen', this.imagen);
      this._marketingService.crearAnuncioImagen(formData).subscribe(
        {
          next: (mensaje: MensajeDTO) => {
            this.informacion.informarExito(mensaje.mensaje);
            this.limpiar();
          },
          error: (error) => {
            this.informacion.informarError(error.error.mensaje || 'Error al crear el anuncio');
          }
        }
      );
    } else {
      this.informacion.informarError('Seleccionar una imagen');
      this.inputFotoTocado = true;
      return;
    }
  }

  cargarAnuncio(anuncio: AnuncioDTO) {
    this._marketingService.crearAnuncio(anuncio).subscribe(
      {
        next: (mensaje : MensajeDTO) => {
          this.informacion.informarExito(mensaje.mensaje);
          this.limpiar();
        },
        error: (error) => {
          this.informacion.informarError(error.error.mensaje || 'Error al crear el anuncio');
        }
      }
    )
  }

  tipoSeleccionado(event: Event) {
      const selectElement = event.target as HTMLSelectElement;
      const selectedValue = selectElement.value;

      if(selectedValue === TipoAnuncio.IMAGEN){
        this.pedirFoto = true;
        this.pedirUrlVideo = false;
        this.quitarVideo();
        this.tipo = this.precios.imagen;
      } else if(selectedValue === TipoAnuncio.VIDEO){
        this.pedirFoto = false;
        this.pedirUrlVideo = true;
        this.inputFotoTocado = false;
        this.agregarVideo();
        this.tipo = this.precios.video;
      } else {
        this.inputFotoTocado = false;
        this.pedirFoto = false;
        this.pedirUrlVideo = false;
        this.quitarVideo();
        this.tipo = this.precios.texto;
      }
      this.imagen = null;
      this.precioFinal = this.dias * this.tipo;
  }

  diasSeleccionados(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const selectedValue = selectElement.value;
    const dias = parseInt(selectedValue);
    if (!isNaN(dias) && dias > 0) {
      this.dias = dias;
      this.precioFinal = this.dias * this.tipo;
    } else {
      this.dias = 0;
      this.precioFinal = 0;
    }
  }

  private quitarVideo(){
    if (this.formAnuncio.contains('urlVideo')) {
      this.formAnuncio.removeControl('urlVideo');
    }
  }

  private agregarVideo(){
    if (!this.formAnuncio.contains('urlVideo')) {
      this.formAnuncio.addControl('urlVideo', this.formBuilder.control('', [Validators.required, Validators.maxLength(250)]));
    }
  }

  limpiar() {
    this.formAnuncio.reset();
    this.imagen = null;
    this.inputFotoTocado = false;
    this.pedirFoto = false;
    this.pedirUrlVideo = false;
    this.precioFinal = 0;
    this.dias = 0;
    this.tipo = 0;
    this.quitarVideo();
  }

  archivoSeleccionado(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.imagen = fileInput.files[0];
    } else {
      this.imagen = null;
    }
  }

  inputFoto(){
    this.inputFotoTocado = true;
  }

  faltaTipo(){
    return this.validadorForm.hasErrors('tipo', 'required');
  }

  esTipoValido(){
    return this.validadorForm.esValido('tipo');
  }

  faltaTexto(){
    return this.validadorForm.hasErrors('texto', 'required');
  }

  esTextoInvalido(){
    return this.validadorForm.hasErrors('texto', 'minlength') || this.validadorForm.hasErrors('texto', 'maxlength');
  }

  esTextoValido(){
    return this.validadorForm.esValido('texto');
  }

  faltaFecha(){
    return this.validadorForm.hasErrors('fechaPublicacion', 'required');
  }

  esFechaValida(){
    return this.validadorForm.esValido('fechaPublicacion');
  }

  faltaDias(){
    return this.validadorForm.hasErrors('dias', 'required');
  }

  sonDiasInvalidos(){
    return this.validadorForm.hasErrors('dias', 'min');
  }

  sonDiasValidos(){
    return this.validadorForm.esValido('dias');
  }

  faltaUrlVideo(){
    return this.validadorForm.hasErrors('urlVideo', 'required');
  }

  esUrlVideoInvalido(){
    return this.validadorForm.hasErrors('urlVideo', 'maxlength');
  }

  esUrlVideoValido(){
    return this.validadorForm.esValido('urlVideo');
  }

  faltaFoto() {
    return this.imagen === null && this.inputFotoTocado;
  }

  esFotoValida() {
    return this.imagen !== null && this.inputFotoTocado;
  }

}