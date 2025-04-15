import { Component, Input } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { Informacion } from '../../models/informacion';

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
  @Input({required: true})
  informacion!: Informacion;

  borrarEmpleado(dpi: string): void {
    const index = this.empleadosDelServicio.findIndex(empleado => empleado.dpi === dpi);
    if (index !== -1) {
      this.empleadosDelServicio.splice(index, 1);
    } else {
      this.informacion.informarError('El empleado no se encuentra en la lista');
    }
  }
}
