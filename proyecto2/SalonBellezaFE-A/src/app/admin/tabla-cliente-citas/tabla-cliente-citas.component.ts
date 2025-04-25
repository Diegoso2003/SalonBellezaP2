import { Component, Input } from '@angular/core';
import { CitasCliente } from '../../models/citasCliente';
import { HoraPipePipe } from '../../hora-pipe.pipe';

@Component({
  selector: 'app-tabla-cliente-citas',
  standalone: true,
  imports: [HoraPipePipe],
  templateUrl: './tabla-cliente-citas.component.html',
  styleUrl: './tabla-cliente-citas.component.scss'
})
export class TablaClienteCitasComponent {

  @Input({required: true})
  clienteCitas!: CitasCliente;
}
