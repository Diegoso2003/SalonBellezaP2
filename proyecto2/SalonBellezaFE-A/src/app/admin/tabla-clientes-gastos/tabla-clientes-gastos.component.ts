import { Component, Input } from '@angular/core';
import { GastosCliente } from '../../models/gastosCliente';

@Component({
  selector: 'app-tabla-clientes-gastos',
  standalone: true,
  imports: [],
  templateUrl: './tabla-clientes-gastos.component.html',
  styleUrl: './tabla-clientes-gastos.component.scss'
})
export class TablaClientesGastosComponent {
  @Input({required: true})
  datos!: GastosCliente;
}
