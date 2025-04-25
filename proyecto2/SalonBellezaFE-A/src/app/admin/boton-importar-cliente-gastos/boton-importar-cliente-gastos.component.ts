import { Component, Input } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';

@Component({
  selector: 'app-boton-importar-cliente-gastos',
  standalone: true,
  imports: [],
  templateUrl: './boton-importar-cliente-gastos.component.html',
  styleUrl: './boton-importar-cliente-gastos.component.scss'
})
export class BotonImportarClienteGastosComponent {
@Input({required: true})
clienteMasGasto!: boolean;

@Input({required: true})
informeCambio!: InformeCambio;
}
