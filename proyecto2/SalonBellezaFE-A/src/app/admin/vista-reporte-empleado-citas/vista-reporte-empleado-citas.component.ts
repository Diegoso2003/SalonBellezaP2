import { Component } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';
import { EmpleadoGanancias } from '../../models/empleadoGanancias';
import { InformacionComponent } from "../../informacion/informacion.component";
import { FormEmpleadoCitasComponent } from "../form-empleado-citas/form-empleado-citas.component";
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { BotonImportarServicioGananciasComponent } from "../boton-importar-servicio-ganancias/boton-importar-servicio-ganancias.component";
import { IntervaloVistaComponent } from "../../compartido/intervalo-vista/intervalo-vista.component";
import { TablaEmpleadoInformeComponent } from "../tabla-empleado-informe/tabla-empleado-informe.component";

@Component({
  selector: 'app-vista-reporte-empleado-citas',
  standalone: true,
  imports: [InformacionComponent, FormEmpleadoCitasComponent, BotonAtrasComponent, BotonImportarServicioGananciasComponent, IntervaloVistaComponent, TablaEmpleadoInformeComponent],
  templateUrl: './vista-reporte-empleado-citas.component.html',
  styleUrl: './vista-reporte-empleado-citas.component.scss'
})
export class VistaReporteEmpleadoCitasComponent {
  informeCambio: InformeCambio = new InformeCambio();
  empleadoGananciasArreglo: EmpleadoGanancias[] = [];
}
