import { Component, inject } from '@angular/core';
import { InformacionComponent } from "../../informacion/informacion.component";
import { InformeCambio } from '../../class/informeCambio';
import { FormAgendaComponent } from "../form-agenda/form-agenda.component";
import { Cita } from '../../models/cita';
import { VistaCitaComponent } from "../../compartido/vista-cita/vista-cita.component";
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { BotonAtendidaComponent } from "../boton-atendida/boton-atendida.component";
import { BotonAusenteComponent } from "../boton-ausente/boton-ausente.component";

@Component({
  selector: 'app-vista-citas',
  standalone: true,
  imports: [InformacionComponent, FormAgendaComponent, VistaCitaComponent, BotonAtrasComponent, BotonAtendidaComponent, BotonAusenteComponent],
  templateUrl: './vista-citas.component.html',
  styleUrl: './vista-citas.component.scss'
})
export class VistaCitasComponent {
  informeCambio: InformeCambio = new InformeCambio();
  citas: Cita[] = [];

}
