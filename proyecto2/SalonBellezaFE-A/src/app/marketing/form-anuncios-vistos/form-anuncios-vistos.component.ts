import { Component, inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { NgClass } from '@angular/common';
import { Anuncio } from '../../models/anuncio';
import { InformacionService } from '../../services/informacion.service';
import { Consulta } from '../../DTO/consulta';
import { MarketingService } from '../../services/marketing/marketing.service';
import { InformeCambio } from '../../class/informeCambio';

@Component({
  selector: 'app-form-anuncios-vistos',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass],
  templateUrl: './form-anuncios-vistos.component.html',
  styleUrl: './form-anuncios-vistos.component.scss'
})
export class FormAnunciosVistosComponent {
  consultaForm: FormGroup;
  private validador: Validador;
  private informacion = inject(InformacionService);
  private marketingService = inject(MarketingService);
  @Input({ required: true })
  anunciosMasVistos!: boolean;

  @Input({ required: true })
  anuncios!: Anuncio[];

  @Input({ required: true })
  informeCambio!: InformeCambio;

  constructor(private formBuilder: FormBuilder){
    this.consultaForm = this.formBuilder.group(
      {
      fechaInicio: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      fechaFin: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]]
      }
    );
    this.validador = new Validador(this.consultaForm);
  }

  enviar(){
    this.vaciarFechas();
    if(this.consultaForm.valid){
      this.mandarInfo();
    } else {
      this.consultaForm.markAllAsTouched();
      this.informacion.informarError('ingresar correctamente los datos solicitados');
    }
  }

  private mandarInfo(){
    const consulta: Consulta = this.armarConsulta();
    if(this.anunciosMasVistos){
      this.marketingService.anunciosMasVistos(consulta).subscribe(
        {
          next: (anuncios: Anuncio[]) => {
            this.agregarAnuncios(anuncios);
            this.informarCambio(consulta);
          },
          error: (error) => {
            this.informacion.informarError(error.error.mensaje || 'error al cargar los anuncios');
          }
        }
      );
    }else{
      this.marketingService.anunciosMenosVistos(consulta).subscribe(
        {
          next: (anuncios: Anuncio[]) => {
            this.agregarAnuncios(anuncios);
            this.informarCambio(consulta);
          },
          error: (error) => {
            this.informacion.informarError(error.error.mensaje || 'error al cargar los anuncios');
          }
        }
      );
    }
    
  }

  private agregarAnuncios(anuncios: Anuncio[]){
    this.anuncios.length = 0;
    this.anuncios.push(...anuncios);
  }

  private informarCambio(consulta: Consulta){
    this.informeCambio.setFechaInicio(consulta.fechaInicio || '');
    this.informeCambio.setFechaFin(consulta.fechaFin || '');
    this.informeCambio.setCambio(true);
  }

  private vaciarFechas(){
    this.informeCambio.setFechaInicio('');
    this.informeCambio.setFechaFin('');
  }

  private armarConsulta(): Consulta {
    const fechaInicio = this.consultaForm.get('fechaInicio')?.value;
    const fechaFin = this.consultaForm.get('fechaFin')?.value;
    let consulta: Consulta = {}
    if(fechaInicio !== ''){
      consulta.fechaInicio = fechaInicio;
    }
    if(fechaFin !== ''){
      consulta.fechaFin = fechaFin;
    }
    return consulta;
  }

  esFechaInicioInvalida(){
    return this.validador.hasErrors('fechaInicio', 'pattern');
  }

  esFechaFinInvalida(){
    return this.validador.hasErrors('fechaFin', 'pattern');
  }

  esFechaInicioValida(){
    return this.validador.esValido('fechaInicio');
  }

  esFechaFinValida(){
    return this.validador.esValido('fechaFin');
  }
}
