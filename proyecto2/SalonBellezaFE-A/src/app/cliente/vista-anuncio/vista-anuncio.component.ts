import { Component, inject, Input, OnInit } from '@angular/core';
import { HistorialAnuncio } from '../../models/historialAnuncio';
import { Anuncio } from '../../models/anuncio';
import { MarketingService } from '../../services/marketing/marketing.service';
import { Router } from '@angular/router';
import { NgClass } from '@angular/common';
import { AnuncioComponent } from "../../anuncios/anuncio/anuncio.component";

@Component({
  selector: 'app-vista-anuncio',
  standalone: true,
  imports: [NgClass, AnuncioComponent],
  templateUrl: './vista-anuncio.component.html',
  styleUrl: './vista-anuncio.component.scss'
})
export class VistaAnuncioComponent implements OnInit {
  anuncioRecuperado: boolean = false;
  anuncio!: Anuncio;
  private _anuncioService = inject(MarketingService);
  private _router = inject(Router);

  url!: string;

  @Input({ required: true })
  derecho!: boolean;

  ngOnInit(): void {
    this.recuperarAnuncio();      
  }

  guardarHistorial(){
    this.url = window.location.origin + this._router.url;
    let historial: HistorialAnuncio = {
      idAnuncio: this.anuncio.idAnuncio,
      url: this.url,
      fechaPublicacion: this.anuncio.vigencia.fechaPublicacion,
      cantidad: 0,
    }
    this._anuncioService.reporteAnuncio(historial).subscribe({
      next: () => {
        console.log('historial guardado');
      },
      error: (error) => {
        console.log(error.error.mensaje || 'Error al guardar historial');
      }
    })
  }

  recuperarAnuncio(){
    this._anuncioService.obtenerAnuncioQueSeMostrara().subscribe({
      next: (anuncio: Anuncio) => {
        this.anuncio = anuncio;
        this.anuncioRecuperado = true;
        this.guardarHistorial();
      },
      error: (error) => {
        console.log(error.error.mensaje || 'Error al recuperar anuncio');
        this.anuncioRecuperado = false;
      }
    });
  }
}
