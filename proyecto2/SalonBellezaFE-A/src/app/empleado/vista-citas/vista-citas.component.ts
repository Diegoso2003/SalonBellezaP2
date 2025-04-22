import { Component, inject } from '@angular/core';
import { InformacionComponent } from "../../informacion/informacion.component";
import { InformeCambio } from '../../class/informeCambio';
import { FormAgendaComponent } from "../form-agenda/form-agenda.component";
import { Cita } from '../../models/cita';

@Component({
  selector: 'app-vista-citas',
  standalone: true,
  imports: [InformacionComponent, FormAgendaComponent],
  templateUrl: './vista-citas.component.html',
  styleUrl: './vista-citas.component.scss'
})
export class VistaCitasComponent {
  informeCambio: InformeCambio = new InformeCambio();
  citas!: Cita[];

}
