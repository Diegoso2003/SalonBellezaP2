import { Component, inject, Input, OnInit } from '@angular/core';
import { UsuarioSeleccionado } from '../../class/usuarioSeleccionado';
import { CitasEmpleadoDiaDTO } from '../../DTO/CitasEmpleadoDiaDTO';
import { ClienteService } from '../../services/cliente/cliente.service';
import { InformacionService } from '../../services/informacion.service';
import { HorarioEmpleadoDTO } from '../../DTO/horarioEmpleadoDTO';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-horario-empleado',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './horario-empleado.component.html',
  styleUrl: './horario-empleado.component.scss'
})
export class HorarioEmpleadoComponent implements OnInit{
  @Input({ required: true })
  usuarioSeleccionado!: UsuarioSeleccionado;
  private _clienteService = inject(ClienteService);
  private informacion = inject(InformacionService);
  horarioEmpleado: HorarioEmpleadoDTO[] = [];

  ngOnInit(): void {
      this.recuperarHorario();
  }

  private recuperarHorario(): void {
    if(this.usuarioSeleccionado.sonAmbosValidos() && this.usuarioSeleccionado.getUsuarioEncontrado()){
      const citas: CitasEmpleadoDiaDTO = {
        dpi: this.usuarioSeleccionado.getUsuarioSeleccionado().dpi,
        fecha: this.usuarioSeleccionado.getFechaSeleccionada()
      }
      this._clienteService.obtenerHorarioEmpleado(citas).subscribe(
        {
          next: (citas: HorarioEmpleadoDTO[]) => {
            this.horarioEmpleado = citas;
          },
          error: (error) => {
            console.error('Error al obtener el horario del empleado:', error);
            this.informacion.informarError(error.error.mensaje || 'Error al obtener el horario del empleado');
          }
        }
      );
    }
  }
}
