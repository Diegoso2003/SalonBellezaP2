import { NgClass } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { InformeCambio } from '../../class/informeCambio';
import { Consulta } from '../../DTO/consulta';
import { AdminService } from '../../services/admin/admin.service';
import { GastosCliente } from '../../models/gastosCliente';
import { InformacionService } from '../../services/informacion.service';

@Component({
  selector: 'app-form-cliente-gastos',
  standalone: true,
  imports: [NgClass, ReactiveFormsModule],
  templateUrl: './form-cliente-gastos.component.html',
  styleUrl: './form-cliente-gastos.component.scss'
})
export class FormClienteGastosComponent {
  clienteGastosForm!: FormGroup;
  
  private validador: Validador;
  private adminService = inject(AdminService);
  private informacion = inject(InformacionService);

  @Input({required: true})
  clienteMasGasto!: boolean;

  @Input({required: true})
  informeCambio!: InformeCambio;

  @Input({required:true})
  gastosClienteArreglo!: GastosCliente[];

  constructor(private formBuilder: FormBuilder){
    this.clienteGastosForm = this.formBuilder.group(
          {
          fechaInicio: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
          fechaFin: ['', [Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]]
          }
        );
        this.validador = new Validador(this.clienteGastosForm);
  }

  enviar(){
    this.informeCambio.vaciarFechas();
    if(this.clienteGastosForm.valid){
      this.mandarInfo();
    } else {
      this.informacion.informarError('ingresar correctamente los datos ingresados');
      this.clienteGastosForm.markAllAsTouched();
    }
  }

  private mandarInfo(): void{
    const consulta: Consulta = this.armarConsulta();
    if(this.clienteMasGasto){
      this.adminService.obtenerClienteMasGasto(consulta).subscribe(
        {
          next: (gastos: GastosCliente[]) => {
            this.agregarReporte(gastos);
            this.informarCambio(consulta);
          },
          error: (error) => {
            console.log(error);
            this.informacion.informarError(error.error.mensaje || 'error al conseguir los clientes que mas gastan');
          }
        }
      );
    } else {
      this.adminService.obtenerClienteMenorGasto(consulta).subscribe(
        {
          next: (gastos: GastosCliente[]) => {
            this.agregarReporte(gastos);
            this.informarCambio(consulta);
          },
          error: (error) => {
            console.log(error);
            this.informacion.informarError(error.error.mensaje || 'error al conseguir los clientes que menos gastan');
          }
        }
      );
    }
  }

  private agregarReporte(reporte: GastosCliente[]): void{
    this.gastosClienteArreglo.length = 0;
    this.gastosClienteArreglo.push(...reporte);
  }

  private informarCambio(consulta: Consulta): void{
    this.informeCambio.setFechaInicio(consulta.fechaInicio || '');
    this.informeCambio.setFechaFin(consulta.fechaFin || '');
    this.informeCambio.setCambio(true);
  }

  private armarConsulta(): Consulta {
    const fechaInicio = this.clienteGastosForm.get('fechaInicio')?.value;
    const fechaFin = this.clienteGastosForm.get('fechaFin')?.value;
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
