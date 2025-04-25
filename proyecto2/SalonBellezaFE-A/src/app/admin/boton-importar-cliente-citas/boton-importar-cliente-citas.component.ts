import { Component, Input } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';

@Component({
  selector: 'app-boton-importar-cliente-citas',
  standalone: true,
  imports: [],
  templateUrl: './boton-importar-cliente-citas.component.html',
  styleUrl: './boton-importar-cliente-citas.component.scss'
})
export class BotonImportarClienteCitasComponent {
@Input({required: true})
clienteMasCitas!: boolean;

@Input({required: true})
informeCambio!: InformeCambio;
}
