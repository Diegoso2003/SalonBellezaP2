import { Component, Input } from '@angular/core';
import { ReporteServicioCitas } from '../../models/reporteServicioCitas';
import { HoraPipePipe } from '../../hora-pipe.pipe';

@Component({
  selector: 'app-tabla-servicios',
  standalone: true,
  imports: [HoraPipePipe],
  templateUrl: './tabla-servicios.component.html',
  styleUrl: './tabla-servicios.component.scss'
})
export class TablaServiciosComponent {
  @Input({ required: true })
  reporte!: ReporteServicioCitas;
}
