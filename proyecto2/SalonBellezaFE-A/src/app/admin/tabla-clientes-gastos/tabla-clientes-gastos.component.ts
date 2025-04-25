import { Component, Input } from '@angular/core';
import { GastosCliente } from '../../models/gastosCliente';
import { HoraPipePipe } from '../../hora-pipe.pipe';

@Component({
  selector: 'app-tabla-clientes-gastos',
  standalone: true,
  imports: [HoraPipePipe],
  templateUrl: './tabla-clientes-gastos.component.html',
  styleUrl: './tabla-clientes-gastos.component.scss'
})
export class TablaClientesGastosComponent {
  @Input({required: true})
  datos!: GastosCliente;
}
