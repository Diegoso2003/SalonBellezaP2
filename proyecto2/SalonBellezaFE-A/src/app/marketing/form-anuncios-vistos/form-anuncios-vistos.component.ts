import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { NgClass } from '@angular/common';
import { Anuncio } from '../../models/anuncio';

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
  @Input({ required: true })
  anunciosMasVistos!: boolean;

  @Input({ required: true })
  anuncios!: Anuncio[];

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
    if(this.anunciosMasVistos){

    }else {

    }
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
