import { NgClass } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { InformeCambio } from '../../class/informeCambio';
import { ServicioGanancias } from '../../models/servicioGanancias';
import { InformacionService } from '../../services/informacion.service';
import { Consulta } from '../../DTO/consulta';
import { ServiciosService } from '../../services/servicios/servicios.service';

@Component({
  selector: 'app-form-servicio-ganancia',
  standalone: true,
  imports: [NgClass, ReactiveFormsModule],
  templateUrl: './form-servicio-ganancia.component.html',
  styleUrl: './form-servicio-ganancia.component.scss'
})
export class FormServicioGananciaComponent {

  servicioGananciasForm: FormGroup;
  private validador: Validador;
  private informacion = inject(InformacionService);
  private servicioService = inject(ServiciosService);

  @Input({required: true})
  informeCambio!: InformeCambio;

  @Input({required: true})
  servicio!: ServicioGanancias;

  constructor(private fb: FormBuilder){
    this.servicioGananciasForm = this.fb.group({
      fechaInicio: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      fechaFin: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
    });
    this.validador = new Validador(this.servicioGananciasForm);
  }

  private agregarReporte(reporte: ServicioGanancias): void{
    Object.assign(this.servicio, reporte);
  }

  private informarCambio(consulta: Consulta): void{
    this.informeCambio.setFechaInicio(consulta.fechaInicio || '');
    this.informeCambio.setFechaFin(consulta.fechaFin || '');
    this.informeCambio.setCampo(consulta.campo || '');
    this.informeCambio.setCambio(true);
  }

  private armarConsulta(): Consulta {
    const fechaInicio = this.servicioGananciasForm.get('fechaInicio')?.value;
    const fechaFin = this.servicioGananciasForm.get('fechaFin')?.value;
    let consulta: Consulta = {}
    if(fechaInicio !== ''){
      consulta.fechaInicio = fechaInicio;
    }
    if(fechaFin !== ''){
      consulta.fechaFin = fechaFin;
    }
    return consulta;
  }

  private mandarInfo(): void {
    const consulta: Consulta = this.armarConsulta();
    this.servicioService.servicioMayorGanancia(consulta).subscribe(
      {
        next: (reporte: ServicioGanancias) => {
          this.agregarReporte(reporte);
          this.informarCambio(consulta);
        }
        , error: (error) => {
          this.informacion.informarError(error.error.mensaje || 'Error al obtener el reporte');
        }
      }
    );
  }

  enviar(){
    this.informeCambio.vaciarFechas();
    if(this.servicioGananciasForm.valid){
      this.mandarInfo();
    } else {
      this.informacion.informarError('ingresar correctamente los datos ingresados');
      this.servicioGananciasForm.markAllAsTouched();
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
