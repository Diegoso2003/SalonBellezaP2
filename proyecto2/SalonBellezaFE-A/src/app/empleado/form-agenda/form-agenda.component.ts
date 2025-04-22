import { NgClass } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validador } from '../../class/validador-form';
import { InformacionService } from '../../services/informacion.service';
import { UsuarioService } from '../../services/usuario.service';
import { CitasEmpleadoDiaDTO } from '../../DTO/CitasEmpleadoDiaDTO';
import { InformeCambio } from '../../class/informeCambio';
import { Cita } from '../../models/cita';
import { EmpleadoService } from '../../services/empleado/empleado.service';

@Component({
  selector: 'app-form-agenda',
  standalone: true,
  imports: [NgClass, ReactiveFormsModule],
  templateUrl: './form-agenda.component.html',
  styleUrl: './form-agenda.component.scss'
})
export class FormAgendaComponent {
  agendaForm: FormGroup;
  private validador: Validador;
  private informacion = inject(InformacionService);
  private usuarioService = inject(UsuarioService);
  private empleadoService = inject(EmpleadoService);
  @Input({ required: true })
  informeCambio!: InformeCambio;
  @Input({ required: true })
  citas!: Cita[];

  constructor(private formBuilder: FormBuilder) {
    this.agendaForm = this.formBuilder.group({
      fecha: ['', [Validators.required]]
    });
    this.validador = new Validador(this.agendaForm);
  }

  faltaFecha(){
    return this.validador.hasErrors('fecha', 'required');
  }

  esFechaValida(){
    return this.validador.esValido('fecha');
  }

  enviar(){
    if(this.agendaForm.valid){
      let consulta = this.armarDTO();
      this.realizarConsulta(consulta);
    } else {
      this.agendaForm.markAllAsTouched();
      this.informacion.informarError('Ingrese una fecha para realizar la consulta');
    }
  }

  armarDTO(): CitasEmpleadoDiaDTO{
    let consulta = this.agendaForm.value as CitasEmpleadoDiaDTO;
    consulta.dpi = this.usuarioService.getToken() || '';
    return consulta;
  }

  private realizarConsulta(consulta: CitasEmpleadoDiaDTO): void {
    this.empleadoService.obtenerAgenda(consulta).subscribe(
      {
        next: (citas: Cita[]) => {
          this.citas = citas;
          this.informeCambio.setCambio(true);
        },
        error: (error) => {
          this.informacion.informarError(error.error.mensaje || 'Error al obtener la agenda');
          console.error(error);
        }
      }
    );
  }
}
