import { Component } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';
import { EmpleadoGanancias } from '../../models/empleadoGanancias';
import { InformacionComponent } from "../../informacion/informacion.component";
import { FormEmpleadoGananciasComponent } from "../form-empleado-ganancias/form-empleado-ganancias.component";
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { BotonImportarServicioGananciasComponent } from "../boton-importar-servicio-ganancias/boton-importar-servicio-ganancias.component";
import { IntervaloVistaComponent } from "../../compartido/intervalo-vista/intervalo-vista.component";
import { TablaEmpleadoInformeComponent } from "../tabla-empleado-informe/tabla-empleado-informe.component";

@Component({
  selector: 'app-vista-reporte-ganancias-empleado',
  standalone: true,
  imports: [InformacionComponent, FormEmpleadoGananciasComponent, BotonAtrasComponent, BotonImportarServicioGananciasComponent, IntervaloVistaComponent, TablaEmpleadoInformeComponent],
  templateUrl: './vista-reporte-ganancias-empleado.component.html',
  styleUrl: './vista-reporte-ganancias-empleado.component.scss'
})
export class VistaReporteGananciasEmpleadoComponent {
  informeCambio: InformeCambio = new InformeCambio();
  empleadoGananciasArreglo: EmpleadoGanancias[] = [];
}
