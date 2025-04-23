import { Component, inject, Input } from '@angular/core';
import { Cita } from '../../models/cita';
import { InformacionService } from '../../services/informacion.service';
import { EmpleadoService } from '../../services/empleado/empleado.service';

@Component({
  selector: 'app-boton-ausente',
  standalone: true,
  imports: [],
  templateUrl: './boton-ausente.component.html',
  styleUrl: './boton-ausente.component.scss'
})
export class BotonAusenteComponent {

  @Input({ required: true })
  idCita!: number;
  @Input({ required: true })
  citas!: Cita[];

  private informacion = inject(InformacionService);
  private empleadoService = inject(EmpleadoService);

  agendarCitaAusente(): void {
    this.empleadoService.marcarClienteAusente(this.idCita).subscribe(
      {
        next: () => {
          this.informacion.informarExito('Cliente marcado como ausente');
          this.borrarCita();
        },
        error: (error) => {
          this.informacion.informarError(error.error.mensaje || 'Error al marcar al cliente como ausente');
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
