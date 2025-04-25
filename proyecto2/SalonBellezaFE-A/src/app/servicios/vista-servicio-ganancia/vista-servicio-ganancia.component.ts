import { Component } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';
import { ServicioGanancias } from '../../models/servicioGanancias';
import { InformacionComponent } from "../../informacion/informacion.component";
import { FormServicioGananciaComponent } from "../form-servicio-ganancia/form-servicio-ganancia.component";
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { BotonImportarServicioGananciasComponent } from "../../admin/boton-importar-servicio-ganancias/boton-importar-servicio-ganancias.component";
import { IntervaloVistaComponent } from "../../compartido/intervalo-vista/intervalo-vista.component";
import { TablaServicioGananciasComponent } from "../../admin/tabla-servicio-ganancias/tabla-servicio-ganancias.component";

@Component({
  selector: 'app-vista-servicio-ganancia',
  standalone: true,
  imports: [InformacionComponent, FormServicioGananciaComponent, BotonAtrasComponent, BotonImportarServicioGananciasComponent, IntervaloVistaComponent, TablaServicioGananciasComponent],
  templateUrl: './vista-servicio-ganancia.component.html',
  styleUrl: './vista-servicio-ganancia.component.scss'
})
export class VistaServicioGananciaComponent {
  informeCambio: InformeCambio = new InformeCambio();
  servicio: ServicioGanancias = {} as ServicioGanancias;
}
