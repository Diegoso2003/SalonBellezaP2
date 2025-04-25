import { Component, Input } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';

@Component({
  selector: 'app-boton-importar-servicio-ganancias',
  standalone: true,
  imports: [],
  templateUrl: './boton-importar-servicio-ganancias.component.html',
  styleUrl: './boton-importar-servicio-ganancias.component.scss'
})
export class BotonImportarServicioGananciasComponent {
  @Input({required: true})
  informeCambio!: InformeCambio;
}
