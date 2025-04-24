import { Component, inject, OnInit } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';
import { ActivatedRoute } from '@angular/router';
import { ReporteServicioCitas } from '../../models/reporteServicioCitas';
import { InformacionComponent } from "../../informacion/informacion.component";
import { FormServiciosReservadosComponent } from "../form-servicios-reservados/form-servicios-reservados.component";
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { BotonImportarReservacionesComponent } from "../boton-importar-reservaciones/boton-importar-reservaciones.component";
import { IntervaloVistaComponent } from "../../compartido/intervalo-vista/intervalo-vista.component";
import { TablaServiciosComponent } from "../tabla-servicios/tabla-servicios.component";

@Component({
  selector: 'app-vista-reporte-servicio',
  standalone: true,
  imports: [InformacionComponent, FormServiciosReservadosComponent, BotonAtrasComponent, BotonImportarReservacionesComponent, IntervaloVistaComponent, TablaServiciosComponent],
  templateUrl: './vista-reporte-servicio.component.html',
  styleUrl: './vista-reporte-servicio.component.scss'
})
export class VistaReporteServicioComponent implements OnInit{
  informeCambio: InformeCambio = new InformeCambio();
  serviciosMasReservados: boolean = false;
  private route = inject(ActivatedRoute);
  reporte: ReporteServicioCitas[] = [];

  ngOnInit(): void {
    this.serviciosMasReservados = this.route.snapshot.data['servicioMasReservado'] ?? true;
  }
}
