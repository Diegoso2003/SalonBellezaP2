import { Component, inject, OnInit } from '@angular/core';
import { InformeCambio } from '../../class/informeCambio';
import { CitasCliente } from '../../models/citasCliente';
import { ActivatedRoute } from '@angular/router';
import { InformacionComponent } from "../../informacion/informacion.component";
import { FormClienteCitasComponent } from "../form-cliente-citas/form-cliente-citas.component";
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { BotonImportarClienteCitasComponent } from "../boton-importar-cliente-citas/boton-importar-cliente-citas.component";
import { IntervaloVistaComponent } from "../../compartido/intervalo-vista/intervalo-vista.component";
import { TablaClienteCitasComponent } from "../tabla-cliente-citas/tabla-cliente-citas.component";

@Component({
  selector: 'app-vista-reporte-cliente-citas',
  standalone: true,
  imports: [InformacionComponent, FormClienteCitasComponent, BotonAtrasComponent, BotonImportarClienteCitasComponent, IntervaloVistaComponent, TablaClienteCitasComponent],
  templateUrl: './vista-reporte-cliente-citas.component.html',
  styleUrl: './vista-reporte-cliente-citas.component.scss'
})
export class VistaReporteClienteCitasComponent implements OnInit {

  informeCambio: InformeCambio = new InformeCambio();
  clienteMasCitas:boolean = true;
  clientes: CitasCliente[] = [];

  private router = inject(ActivatedRoute);
  
  ngOnInit(): void {
    this.clienteMasCitas = this.router.snapshot.data['clienteMasCitas'] ?? true;
  }
}
