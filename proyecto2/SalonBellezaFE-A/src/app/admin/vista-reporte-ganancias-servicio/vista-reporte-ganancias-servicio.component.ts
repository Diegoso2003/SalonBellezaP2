import { Component } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';
import { ServicioGanancias } from '../../models/servicioGanancias';
import { InformacionComponent } from "../../informacion/informacion.component";
import { FormServicioGananciasComponent } from "../form-servicio-ganancias/form-servicio-ganancias.component";
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { BotonImportarServicioGananciasComponent } from "../boton-importar-servicio-ganancias/boton-importar-servicio-ganancias.component";
import { IntervaloVistaComponent } from "../../compartido/intervalo-vista/intervalo-vista.component";
import { TablaServicioGananciasComponent } from "../tabla-servicio-ganancias/tabla-servicio-ganancias.component";

@Component({
  selector: 'app-vista-reporte-ganancias-servicio',
  standalone: true,
  imports: [InformacionComponent, FormServicioGananciasComponent, BotonAtrasComponent, BotonImportarServicioGananciasComponent, IntervaloVistaComponent, TablaServicioGananciasComponent],
  templateUrl: './vista-reporte-ganancias-servicio.component.html',
  styleUrl: './vista-reporte-ganancias-servicio.component.scss'
})
export class VistaReporteGananciasServicioComponent {
  informeCambio: InformeCambio = new InformeCambio();
  servicios: ServicioGanancias[] = [];
}
