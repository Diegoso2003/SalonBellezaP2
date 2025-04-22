import { Component, Input } from '@angular/core';
import { Cita } from '../../models/cita';
import { HoraPipePipe } from '../../hora-pipe.pipe';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-vista-cita',
  standalone: true,
  imports: [HoraPipePipe, CurrencyPipe],
  templateUrl: './vista-cita.component.html',
  styleUrl: './vista-cita.component.scss'
})
export class VistaCitaComponent {
  @Input({ required: true})
  cita!: Cita;
  @Input()
  vistaCliente: boolean = true;
  @Input()
  vistaAdmin: boolean = false;
}
