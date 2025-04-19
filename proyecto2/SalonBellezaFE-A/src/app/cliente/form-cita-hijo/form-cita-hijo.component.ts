import { Component, inject, Input } from '@angular/core';
import { Servicio } from '../../models/servicio';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgClass } from '@angular/common';
import { InformacionService } from '../../services/informacion.service';
import { Validador } from '../../class/validador-form';
import { FormEmpleadosComponent } from "../../servicios/form-empleados/form-empleados.component";
import { UsuarioSeleccionado } from '../../class/usuarioSeleccionado';

@Component({
  selector: 'app-form-cita-hijo',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, FormEmpleadosComponent],
  templateUrl: './form-cita-hijo.component.html',
  styleUrl: './form-cita-hijo.component.scss'
})
export class FormCitaHijoComponent {
  @Input({ required: true})
  servicio!: Servicio;

  citaform: FormGroup;
  private informacion = inject(InformacionService);
  private validadorForm: Validador;
  usuarioSeleccionado: UsuarioSeleccionado = new UsuarioSeleccionado();

  constructor(private formBuilder: FormBuilder) {
    this.citaform = this.formBuilder.group(
      {
        fecha: ['', [Validators.required]],
        hora: ['', [Validators.required]]
      }
    );
    this.validadorForm = new Validador(this.citaform);
  }

  enviar(){

  }

  faltaFecha(){
    return this.validadorForm.hasErrors('fecha', 'required');
  }

  esFechaValida(){
    return this.validadorForm.esValido('fecha');
  }

  faltaHora(){
    return this.validadorForm.hasErrors('hora', 'required');
  }

  esHoraValida(){
    return this.validadorForm.esValido('hora');
  }
}
