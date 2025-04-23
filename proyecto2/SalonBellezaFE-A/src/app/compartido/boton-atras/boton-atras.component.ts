import { Component, Input } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';

@Component({
  selector: 'app-boton-atras',
  standalone: true,
  imports: [],
  templateUrl: './boton-atras.component.html',
  styleUrl: './boton-atras.component.scss'
})
export class BotonAtrasComponent {

  @Input({ required: true })
  informe!: InformeCambio;

  volver(): void{
    this.informe.setCambio(false);
    this.informe.setFecha('');
  }
}
