import { Component, Input } from '@angular/core';
import { Anuncio } from '../../models/anuncio';

@Component({
  selector: 'app-tabla-anuncios',
  standalone: true,
  imports: [],
  templateUrl: './tabla-anuncios.component.html',
  styleUrl: './tabla-anuncios.component.scss'
})
export class TablaAnunciosComponent {
 @Input({ required: true })
 anuncios!: Anuncio[];
}
