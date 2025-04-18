import { Component, inject, Input, OnInit } from '@angular/core';
import { Anuncio } from '../../models/anuncio';
import { AnuncioImagenComponent } from "../anuncio-imagen/anuncio-imagen.component";
import { AnuncioVideoComponent } from "../anuncio-video/anuncio-video.component";

@Component({
  selector: 'app-anuncio',
  standalone: true,
  imports: [AnuncioImagenComponent, AnuncioVideoComponent],
  templateUrl: './anuncio.component.html',
  styleUrl: './anuncio.component.scss'
})
export class AnuncioComponent{
  @Input({ required: true })
  anuncio!: Anuncio;

}