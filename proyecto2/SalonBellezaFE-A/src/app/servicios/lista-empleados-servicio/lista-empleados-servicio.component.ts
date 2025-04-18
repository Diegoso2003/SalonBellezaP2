import { Component, inject, Input } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { InformacionService } from '../../services/informacion.service';

@Component({
  selector: 'app-lista-empleados-servicio',
  standalone: true,
  imports: [],
  templateUrl: './lista-empleados-servicio.component.html',
  styleUrl: './lista-empleados-servicio.component.scss'
})
export class ListaEmpleadosServicioComponent {

  @Input({required: true})
  empleadosDelServicio!: Usuario[];
  private informacion = inject(InformacionService);

  borrarEmpleado(dpi: string): void {
    const index = this.empleadosDelServicio.findIndex(empleado => empleado.dpi === dpi);
    if (index !== -1) {
      this.empleadosDelServicio.splice(index, 1);
    } else {
      this.informacion.informarError('El empleado no se encuentra en la lista');
    }
  }
}
