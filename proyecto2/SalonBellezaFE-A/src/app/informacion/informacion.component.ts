import { Component, Input } from '@angular/core';
import { Informacion } from '../models/informacion';
import { InformeErrorComponent } from "../compartido/informe-error/informe-error.component";

@Component({
  selector: 'app-informacion',
  standalone: true,
  imports: [InformeErrorComponent],
  templateUrl: './informacion.component.html',
  styleUrl: './informacion.component.scss'
})
export class InformacionComponent{
  @Input({ required: true}) 
  informacion!: Informacion;
  
}
