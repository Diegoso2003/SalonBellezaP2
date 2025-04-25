import { NgClass } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { InformacionService } from '../../services/informacion.service';
import { InformeCambio } from '../../class/informeCambio';
import { Consulta } from '../../DTO/consulta';
import { EmpleadoGanancias } from '../../models/empleadoGanancias';
import { AdminService } from '../../services/admin/admin.service';

@Component({
  selector: 'app-form-empleado-ganancias',
  standalone: true,
  imports: [NgClass, ReactiveFormsModule],
  templateUrl: './form-empleado-ganancias.component.html',
  styleUrl: './form-empleado-ganancias.component.scss'
})
export class FormEmpleadoGananciasComponent {
  empleadoGananciasForm: FormGroup;
  private validador: Validador;
  private informacion = inject(InformacionService);
  private adminService = inject(AdminService);

  @Input({required: true})
  informeCambio!: InformeCambio;

  @Input({required:true})
  empleadoGananciasArreglo!: EmpleadoGanancias[];

  constructor(private formBuilder: FormBuilder){
    this.empleadoGananciasForm = this.formBuilder.group(
      {
      fechaInicio: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      fechaFin: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      dpi: ['', [Validators.pattern(/^[1-9]\d{12}$/)]],
      }
    );
    this.validador = new Validador(this.empleadoGananciasForm);
  }

  enviar(){
    this.informeCambio.vaciarFechas();
    if(this.empleadoGananciasForm.valid){
      this.mandarInfo();
    } else {
      this.informacion.informarError('ingresar correctamente los datos ingresados');
      this.empleadoGananciasForm.markAllAsTouched();
    }
  }

  private mandarInfo(): void {
    const consulta: Consulta = this.armarConsulta();
    this.adminService.obtenerGananciasPorEmpleado(consulta).subscribe(
      {
        next: (reporte: EmpleadoGanancias[]) => {
          this.agregarReporte(reporte);
          this.informarCambio(consulta);
        }
        , error: (error) => {
          this.informacion.informarError(error.error.mensaje || 'Error al obtener el reporte de ganancias por empleado');
          console.error(error);
        }
      }
    );
  }

  private agregarReporte(reporte: EmpleadoGanancias[]): void{
    this.empleadoGananciasArreglo.length = 0;
    this.empleadoGananciasArreglo.push(...reporte);
  }

  private informarCambio(consulta: Consulta): void{
    this.informeCambio.setFechaInicio(consulta.fechaInicio || '');
    this.informeCambio.setFechaFin(consulta.fechaFin || '');
    this.informeCambio.setCampo(consulta.campo || '');
    this.informeCambio.setCambio(true);
  }

  private armarConsulta(): Consulta {
    const fechaInicio = this.empleadoGananciasForm.get('fechaInicio')?.value;
    const fechaFin = this.empleadoGananciasForm.get('fechaFin')?.value;
    const dpi = this.empleadoGananciasForm.get('dpi')?.value;
    let consulta: Consulta = {}
    if(fechaInicio !== ''){
      consulta.fechaInicio = fechaInicio;
    }
    if(fechaFin !== ''){
      consulta.fechaFin = fechaFin;
    }
    if(dpi !== ''){
      consulta.campo = dpi;
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

  esDpiInvalido() {
    return this.validador.hasErrors('dpi', 'pattern');
  }

  esDpiValido() {
    return this.validador.esValido('dpi');
  }  
}
