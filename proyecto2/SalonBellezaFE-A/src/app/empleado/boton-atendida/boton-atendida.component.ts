import { Component, inject, Input } from '@angular/core';
import { Cita } from '../../models/cita';
import { EmpleadoService } from '../../services/empleado/empleado.service';
import { InformacionService } from '../../services/informacion.service';

@Component({
  selector: 'app-boton-atendida',
  standalone: true,
  imports: [],
  templateUrl: './boton-atendida.component.html',
  styleUrl: './boton-atendida.component.scss'
})
export class BotonAtendidaComponent {

  @Input({ required: true })
  idCita!: number;
  @Input({ required: true })
  citas!: Cita[];

  private empleadoService = inject(EmpleadoService);
  private informacion = inject(InformacionService);

  agendarCitaAtendida(): void {
    this.empleadoService.marcarCitaAtendida(this.idCita).subscribe(
      {
        next: () => {
          this.informacion.informarExito('Cita marcada como atendida');
          this.borrarCita();
        },
        error: (error) => {
          this.informacion.informarError(error.error.mensaje || 'Error al marcar la cita como atendida');
        }
      }
    );
  }

  private borrarCita(): void {
    const index = this.citas.findIndex(cita => cita.idCita === this.idCita);
    if (index !== -1) {
      this.citas.splice(index, 1);
    }
  }
}
