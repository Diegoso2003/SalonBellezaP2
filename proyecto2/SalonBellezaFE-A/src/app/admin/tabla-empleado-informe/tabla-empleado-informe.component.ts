import { Component, Input } from '@angular/core';
import { EmpleadoGanancias } from '../../models/empleadoGanancias';
import { HoraPipePipe } from '../../hora-pipe.pipe';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-tabla-empleado-informe',
  standalone: true,
  imports: [HoraPipePipe, CurrencyPipe],
  templateUrl: './tabla-empleado-informe.component.html',
  styleUrl: './tabla-empleado-informe.component.scss'
})
export class TablaEmpleadoInformeComponent {
  @Input({required: true})
  empleado!: EmpleadoGanancias;
}
