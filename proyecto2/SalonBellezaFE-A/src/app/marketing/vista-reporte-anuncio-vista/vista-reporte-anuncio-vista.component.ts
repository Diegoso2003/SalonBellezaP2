import { Component } from '@angular/core';
import { InformacionComponent } from "../../informacion/informacion.component";
import { InformeCambio } from '../../class/informeCambio';
import { Anuncio } from '../../models/anuncio';

@Component({
  selector: 'app-vista-reporte-anuncio-vista',
  standalone: true,
  imports: [InformacionComponent],
  templateUrl: './vista-reporte-anuncio-vista.component.html',
  styleUrl: './vista-reporte-anuncio-vista.component.scss'
})
export class VistaReporteAnuncioVistaComponent {

  informeCambio: InformeCambio = new InformeCambio();
  anuncios: Anuncio[] = [];
}
