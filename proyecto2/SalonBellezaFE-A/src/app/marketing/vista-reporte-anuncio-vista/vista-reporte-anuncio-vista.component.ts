import { Component, inject, OnInit } from '@angular/core';
import { InformacionComponent } from "../../informacion/informacion.component";
import { InformeCambio } from '../../class/informeCambio';
import { Anuncio } from '../../models/anuncio';
import { ActivatedRoute } from '@angular/router';
import { FormAnunciosVistosComponent } from "../form-anuncios-vistos/form-anuncios-vistos.component";
import { BotonAtrasComponent } from "../../compartido/boton-atras/boton-atras.component";
import { TablaAnunciosComponent } from "../tabla-anuncios/tabla-anuncios.component";
import { BotonImportarComponent } from "../boton-importar/boton-importar.component";
import { IntervaloVistaComponent } from "../../compartido/intervalo-vista/intervalo-vista.component";

@Component({
  selector: 'app-vista-reporte-anuncio-vista',
  standalone: true,
  imports: [InformacionComponent, FormAnunciosVistosComponent, BotonAtrasComponent, TablaAnunciosComponent, BotonImportarComponent, IntervaloVistaComponent],
  templateUrl: './vista-reporte-anuncio-vista.component.html',
  styleUrl: './vista-reporte-anuncio-vista.component.scss'
})
export class VistaReporteAnuncioVistaComponent implements OnInit{

  informeCambio: InformeCambio = new InformeCambio();
  anuncios: Anuncio[] = [];
  anunciosMasVistos: boolean = false;
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.anunciosMasVistos = this.route.snapshot.data['anunciosMasVistos'] ?? true;
  }
}
