import { Component, Input } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';

@Component({
  selector: 'app-boton-importar-reservaciones',
  standalone: true,
  imports: [],
  templateUrl: './boton-importar-reservaciones.component.html',
  styleUrl: './boton-importar-reservaciones.component.scss'
})
export class BotonImportarReservacionesComponent {
  @Input({ required: true })
  informeCambio!: InformeCambio;

  @Input({ required: true })
  serviciosMasReservados!: boolean;
}
