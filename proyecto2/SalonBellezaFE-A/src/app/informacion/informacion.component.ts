import { Component, Input } from '@angular/core';
import { Informacion } from '../models/informacion';
import { InformeErrorComponent } from "../compartido/informe-error/informe-error.component";
import { InformeExitoComponent } from "../compartido/informe-exito/informe-exito.component";

@Component({
  selector: 'app-informacion',
  standalone: true,
  imports: [InformeErrorComponent, InformeExitoComponent],
  templateUrl: './informacion.component.html',
  styleUrl: './informacion.component.scss'
})
export class InformacionComponent{
  @Input({ required: true}) 
  informacion!: Informacion;
  
}
