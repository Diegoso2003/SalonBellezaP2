import { NgClass } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { InformacionService } from '../../services/informacion.service';
import { InformeCambio } from '../../class/informeCambio';
import { ServicioGanancias } from '../../models/servicioGanancias';
import { AdminService } from '../../services/admin/admin.service';
import { Consulta } from '../../DTO/consulta';

@Component({
  selector: 'app-form-servicio-ganancias',
  standalone: true,
  imports: [NgClass, ReactiveFormsModule],
  templateUrl: './form-servicio-ganancias.component.html',
  styleUrl: './form-servicio-ganancias.component.scss'
})
export class FormServicioGananciasComponent {
  servicioGananciasForm: FormGroup;

  private validador: Validador;
  private informacion = inject(InformacionService);
  private adminService = inject(AdminService);

  @Input({required: true})
  informeCambio!: InformeCambio;

  @Input({required: true})
  servicios!: ServicioGanancias[];

  constructor(private fb: FormBuilder){
    this.servicioGananciasForm = this.fb.group({
      fechaInicio: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      fechaFin: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      servicio: ['']
    });
    this.validador = new Validador(this.servicioGananciasForm);
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

  private mandarInfo(): void{
    const consulta: Consulta = this.armarConsulta();
    this.adminService.obtenerServiciosGanancia(consulta).subscribe(
      {
        next: (reporte: ServicioGanancias[]) => {
          this.agregarReporte(reporte);
          this.informarCambio(consulta);
        },
        error: (error) => {
          this.informacion.informarError(error.error.mensaje || 'Error al obtener el reporte');
        }
      }
    );
  }

  private agregarReporte(reporte: ServicioGanancias[]): void{
    this.servicios.length = 0;
    this.servicios.push(...reporte);
    const totalGanancia = reporte.reduce((sum, servicio) => sum + servicio.totalGanancias, 0);
    this.informeCambio.setSuma(totalGanancia);
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
    const servicio = this.servicioGananciasForm.get('servicio')?.value;
    let consulta: Consulta = {}
    if(fechaInicio !== ''){
      consulta.fechaInicio = fechaInicio;
    }
    if(fechaFin !== ''){
      consulta.fechaFin = fechaFin;
    }
    if(servicio !== ''){
      consulta.campo = servicio;
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
  
  esServicioValido(){
    return this.validador.esValido('servicio');
  }
}
