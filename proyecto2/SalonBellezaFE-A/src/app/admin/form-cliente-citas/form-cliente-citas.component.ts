import { NgClass } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { InformeCambio } from '../../class/informeCambio';
import { CitasCliente } from '../../models/citasCliente';
import { InformacionService } from '../../services/informacion.service';
import { AdminService } from '../../services/admin/admin.service';
import { Consulta } from '../../DTO/consulta';

@Component({
  selector: 'app-form-cliente-citas',
  standalone: true,
  imports: [NgClass, ReactiveFormsModule],
  templateUrl: './form-cliente-citas.component.html',
  styleUrl: './form-cliente-citas.component.scss'
})
export class FormClienteCitasComponent {
  clienteCitasForm: FormGroup;
  private validador: Validador;

  private informacion = inject(InformacionService);
  private adminService = inject(AdminService);

  @Input({required: true})
  clienteMasCitas!: boolean;

  @Input({required: true})
  informeCambio!: InformeCambio;

  @Input({required: true})
  citasClienteArreglo!: CitasCliente[];

  constructor(private formBuilder: FormBuilder) {
    this.clienteCitasForm = this.formBuilder.group({
      fechaInicio: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      fechaFin: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]]
    });
    this.validador = new Validador(this.clienteCitasForm);
  }

  enviar(){
    this.informeCambio.vaciarFechas();
    if(this.clienteCitasForm.valid){
      this.mandarInfo();
    } else {
      this.informacion.informarError('ingresar correctamente los datos ingresados');
      this.clienteCitasForm.markAllAsTouched();
    }
  }

  private mandarInfo(): void{
    const consulta: Consulta = this.armarConsulta();
    if(this.clienteMasCitas){
      this.adminService.obtenerClienteMasCitas(consulta).subscribe(
        {
          next: (citas: CitasCliente[]) => {
            this.agregarReporte(citas);
            this.informarCambio(consulta);
          },
          error: (error) => {
            this.informacion.informarError(error.error.mensaje || 'Error al obtener los datos');
          }
        }
      );
    } else {
      this.adminService.obtenerClienteMenosCitas(consulta).subscribe(
        {
          next: (citas: CitasCliente[]) => {
            this.agregarReporte(citas);
            this.informarCambio(consulta);
          },
          error: (error) => {
            this.informacion.informarError(error.error.mensaje || 'Error al obtener los datos');
          }
        }
      );
    }
  }

  private agregarReporte(reporte: CitasCliente[]): void{
    this.citasClienteArreglo.length = 0;
    this.citasClienteArreglo.push(...reporte);
  }

  private informarCambio(consulta: Consulta): void{
    this.informeCambio.setFechaInicio(consulta.fechaInicio || '');
    this.informeCambio.setFechaFin(consulta.fechaFin || '');
    this.informeCambio.setCampo(consulta.campo || '');
    this.informeCambio.setCambio(true);
  }

  private armarConsulta(): Consulta {
    const fechaInicio = this.clienteCitasForm.get('fechaInicio')?.value;
    const fechaFin = this.clienteCitasForm.get('fechaFin')?.value;
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
