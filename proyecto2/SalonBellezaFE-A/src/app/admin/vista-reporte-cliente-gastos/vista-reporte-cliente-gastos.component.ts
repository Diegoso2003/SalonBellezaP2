import { Component, inject, OnInit } from '@angular/core';
import { InformacionComponent } from "../../informacion/informacion.component";
import { InformeCambio } from '../../class/informeCambio';
import { FormClienteGastosComponent } from "../form-cliente-gastos/form-cliente-gastos.component";
import { GastosCliente } from '../../models/gastosCliente';
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { IntervaloVistaComponent } from "../../compartido/intervalo-vista/intervalo-vista.component";
import { BotonImportarClienteGastosComponent } from "../boton-importar-cliente-gastos/boton-importar-cliente-gastos.component";
import { TablaClientesGastosComponent } from "../tabla-clientes-gastos/tabla-clientes-gastos.component";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-vista-reporte-cliente-gastos',
  standalone: true,
  imports: [InformacionComponent, FormClienteGastosComponent, BotonAtrasComponent, IntervaloVistaComponent, BotonImportarClienteGastosComponent, TablaClientesGastosComponent],
  templateUrl: './vista-reporte-cliente-gastos.component.html',
  styleUrl: './vista-reporte-cliente-gastos.component.scss'
})
export class VistaReporteClienteGastosComponent implements OnInit{
  informeCambio: InformeCambio = new InformeCambio();
  clienteMasGasto: boolean = true;
  clientes: GastosCliente[] = [];

  private router = inject(ActivatedRoute);

  ngOnInit(): void {
    this.clienteMasGasto = this.router.snapshot.data['clienteMasGasto'] ?? true;
  }
}
