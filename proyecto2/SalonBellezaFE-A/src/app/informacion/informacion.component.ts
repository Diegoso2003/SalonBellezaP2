import { Component, inject } from '@angular/core';
import { InformeErrorComponent } from "../compartido/informe-error/informe-error.component";
import { InformeExitoComponent } from "../compartido/informe-exito/informe-exito.component";
import { InformacionService } from '../services/informacion.service';

@Component({
  selector: 'app-informacion',
  standalone: true,
  imports: [InformeErrorComponent, InformeExitoComponent],
  templateUrl: './informacion.component.html',
  styleUrl: './informacion.component.scss'
})
export class InformacionComponent{
  informacion = inject(InformacionService);
  
}
