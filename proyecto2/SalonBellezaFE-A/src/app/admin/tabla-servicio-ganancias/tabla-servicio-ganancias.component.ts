import { Component, Input } from '@angular/core';
import { ServicioGanancias } from '../../models/servicioGanancias';
import { HoraPipePipe } from '../../hora-pipe.pipe';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-tabla-servicio-ganancias',
  standalone: true,
  imports: [HoraPipePipe, CurrencyPipe],
  templateUrl: './tabla-servicio-ganancias.component.html',
  styleUrl: './tabla-servicio-ganancias.component.scss'
})
export class TablaServicioGananciasComponent {
  @Input({required: true})
  servicio!: ServicioGanancias;
}
