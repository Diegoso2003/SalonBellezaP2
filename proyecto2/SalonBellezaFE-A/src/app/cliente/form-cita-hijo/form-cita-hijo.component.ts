import { Component, inject, Input, OnInit } from '@angular/core';
import { Servicio } from '../../models/servicio';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgClass } from '@angular/common';
import { InformacionService } from '../../services/informacion.service';
import { Validador } from '../../class/validador-form';
import { FormEmpleadosComponent } from "../../servicios/form-empleados/form-empleados.component";
import { UsuarioSeleccionado } from '../../class/usuarioSeleccionado';
import { HorarioEmpleadoComponent } from "../horario-empleado/horario-empleado.component";
import { UsuarioService } from '../../services/usuario.service';
import { ClienteService } from '../../services/cliente/cliente.service';
import { CitaDTO } from '../../DTO/citaDTO';
import { MensajeDTO } from '../../DTO/mensajeDTO';
import { HorarioSalon } from '../../models/horarioSalon';
import { HoraPipePipe } from '../../hora-pipe.pipe';

@Component({
  selector: 'app-form-cita-hijo',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, FormEmpleadosComponent, HorarioEmpleadoComponent, HoraPipePipe],
  templateUrl: './form-cita-hijo.component.html',
  styleUrl: './form-cita-hijo.component.scss'
})
export class FormCitaHijoComponent implements OnInit{
  @Input({ required: true})
  servicio!: Servicio;

  citaform: FormGroup;
  horarioSalon!: HorarioSalon;
  private informacion = inject(InformacionService);
  private _usuarioService = inject(UsuarioService);
  private _clienteService = inject(ClienteService);
  private validadorForm: Validador;
  usuarioSeleccionado: UsuarioSeleccionado = new UsuarioSeleccionado();

  mostrarListaEmpleados: boolean = true;

  constructor(private formBuilder: FormBuilder) {
    this.citaform = this.formBuilder.group(
      {
        fecha: ['', [Validators.required]],
        hora: ['', [Validators.required]]
      }
    );
    this.validadorForm = new Validador(this.citaform);
  }

  ngOnInit(): void {
    this.obtenerHorarioSalon();
  }

  private obtenerHorarioSalon(): void {
    this._clienteService.obtenerHorarioSalon().subscribe(
      {
        next: (horario: HorarioSalon) => {
          this.horarioSalon = horario;
          console.log('Horario del salón:', this.horarioSalon);
        },
        error: (error) => {
          console.error(error);
        }
      }
    );
  }

  private limpiarFormulario(): void {
    this.mostrarListaEmpleados = false;
    this.citaform.reset();
    this.usuarioSeleccionado.setUsuarioSeleccionado(undefined);
    this.usuarioSeleccionado.setFechaSeleccionada(null);
    this.usuarioSeleccionado.setUsuarioEncontrado(false);
    setTimeout(() => {
      this.mostrarListaEmpleados = true;
    }, 20);
  }

  enviar(){
    if (this.citaform.valid) {
      if(this.usuarioSeleccionado.sonAmbosValidos()){
        let dpiCliente = this._usuarioService.getToken();
        let dpiEmpleado = this.usuarioSeleccionado.getUsuarioSeleccionado().dpi;
        let cita: CitaDTO = this.citaform.value as CitaDTO;
        cita.dpiCliente = dpiCliente || '';
        cita.dpiEmpleado = dpiEmpleado;
        cita.idServicio = this.servicio.idServicio;
        this._clienteService.agendarCita(cita).subscribe(
          {
            next: (respuesta: MensajeDTO) => {
              this.informacion.informarExito(respuesta.mensaje);
              this.limpiarFormulario();
            },
            error: (error) =>  {
              this.informacion.informarError(error.error.mensaje || 'Error al agendar la cita');
            }
          }
        );
      } else {
        this.informacion.informarError('Seleccionar empleado y horario');
      }
    } else {
      this.informacion.informarError('Completar la informacion requerida');
      this.citaform.markAllAsTouched();
    }
  }

  fechaSeleccionada(event: Event): void{
    const selectElement = event.target as HTMLSelectElement;
    const selectedValue: string = selectElement.value;
    if (selectedValue.length > 0){
      this.usuarioSeleccionado.setFechaSeleccionada(selectedValue);
    } else {
      this.usuarioSeleccionado.setFechaSeleccionada(null);
    }
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
