import { Component, Input } from '@angular/core';
import { Informacion } from '../models/informacion';

@Component({
  selector: 'app-informacion',
  standalone: true,
  imports: [],
  templateUrl: './informacion.component.html',
  styleUrl: './informacion.component.scss'
})
export class InformacionComponent {
  @Input({ required: true}) 
  informacion!: Informacion;
}
