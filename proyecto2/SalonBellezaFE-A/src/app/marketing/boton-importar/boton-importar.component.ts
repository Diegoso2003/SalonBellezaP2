import { Component, Input } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';

@Component({
  selector: 'app-boton-importar',
  standalone: true,
  imports: [],
  templateUrl: './boton-importar.component.html',
  styleUrl: './boton-importar.component.scss'
})
export class BotonImportarComponent {
@Input({ required: true })
informeCambio!: InformeCambio;

@Input({ required: true })
anunciosMasVistos!: boolean;
}
