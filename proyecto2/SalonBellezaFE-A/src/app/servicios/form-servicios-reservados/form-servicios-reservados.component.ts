import { NgClass } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { InformeCambio } from '../../class/informeCambio';
import { ReporteServicioCitas } from '../../models/reporteServicioCitas';
import { InformacionService } from '../../services/informacion.service';
import { Consulta } from '../../DTO/consulta';
import { ServiciosService } from '../../services/servicios/servicios.service';

@Component({
  selector: 'app-form-servicios-reservados',
  standalone: true,
  imports: [NgClass, ReactiveFormsModule],
  templateUrl: './form-servicios-reservados.component.html',
  styleUrl: './form-servicios-reservados.component.scss'
})
export class FormServiciosReservadosComponent {
  servicioReservadoForm: FormGroup;

  private validador: Validador;
  private informacion = inject(InformacionService);
  private servicioService = inject(ServiciosService);

  @Input({ required: true })
  serviciosMasReservados!: boolean;

  @Input({ required: true })
  informeCambio!: InformeCambio;

  @Input({ required: true })
  datos!: ReporteServicioCitas[];

  constructor(private formBuilder: FormBuilder) {
    this.servicioReservadoForm = this.formBuilder.group(
      {
            fechaInicio: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
            fechaFin: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]]
      }
    );
    this.validador = new Validador(this.servicioReservadoForm);
  }

  enviar() {
    this.informeCambio.vaciarFechas();
    if (this.servicioReservadoForm.valid) {
      this.mandarInfo();
    } else {
      this.servicioReservadoForm.markAllAsTouched();
      this.informacion.informarError('ingresar correctamente los datos solicitados');
    }
  }

  private mandarInfo() {
    const consulta: Consulta = this.armarConsulta();
    if (this.serviciosMasReservados) {
      this.servicioService.serviciosMasReservados(consulta).subscribe(
        {
          next: (reporte: ReporteServicioCitas[]) => {
            this.agregarReporte(reporte);
            this.informarCambio(consulta);
          },
          error: (error) => {
            this.informacion.informarError(error.error.mensaje || 'Error al obtener los servicios más reservados');
          }
        }
      );
    } else {
      this.servicioService.serviciosMenosReservados(consulta).subscribe(
        {
          next: (reporte: ReporteServicioCitas[]) => {
            this.agregarReporte(reporte);
            this.informarCambio(consulta);
          },
          error: (error) => {
            this.informacion.informarError(error.error.mensaje || 'Error al obtener los servicios menos reservados');
          }
        }
      );
    }
  }

  private agregarReporte(anuncios: ReporteServicioCitas[]){
    this.datos.length = 0;
    this.datos.push(...anuncios);
  }

  private informarCambio(consulta: Consulta){
    this.informeCambio.setFechaInicio(consulta.fechaInicio || '');
    this.informeCambio.setFechaFin(consulta.fechaFin || '');
    this.informeCambio.setCambio(true);
  }

  private armarConsulta(): Consulta {
    const fechaInicio = this.servicioReservadoForm.get('fechaInicio')?.value;
    const fechaFin = this.servicioReservadoForm.get('fechaFin')?.value;
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
