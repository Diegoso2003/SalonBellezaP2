import { Component, Input } from '@angular/core';
import { Servicio } from '../../models/servicio';
import { CurrencyPipe } from '@angular/common';
import { BotonAgendarComponent } from "../boton-agendar/boton-agendar.component";
import { BotonCatalogoComponent } from "../boton-catalogo/boton-catalogo.component";
import { DuracionPipePipe } from '../../duracion-pipe.pipe';

@Component({
  selector: 'app-vista-servicio',
  standalone: true,
  imports: [CurrencyPipe, BotonAgendarComponent, BotonCatalogoComponent, DuracionPipePipe],
  templateUrl: './vista-servicio.component.html',
  styleUrl: './vista-servicio.component.scss'
})
export class VistaServicioComponent {
  @Input({required: true})
  servicio!: Servicio;

  @Input({required: true})
  vista_cliente!: boolean;

  @Input({required: true})
  vista_individual!: boolean;
}
