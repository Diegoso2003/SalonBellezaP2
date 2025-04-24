import { Component, Input } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';

@Component({
  selector: 'app-intervalo-vista',
  standalone: true,
  imports: [],
  templateUrl: './intervalo-vista.component.html',
  styleUrl: './intervalo-vista.component.scss'
})
export class IntervaloVistaComponent {
  @Input({ required: true })
  informeCambio!: InformeCambio;
}
