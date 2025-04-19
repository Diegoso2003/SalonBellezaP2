import { Component, inject, Input } from '@angular/core';
import { InformacionService } from '../../services/informacion.service';
import { ServiciosService } from '../../services/servicios/servicios.service';

@Component({
  selector: 'app-boton-catalogo',
  standalone: true,
  imports: [],
  templateUrl: './boton-catalogo.component.html',
  styleUrl: './boton-catalogo.component.scss'
})
export class BotonCatalogoComponent {

  @Input({ required: true })
  idServicio!: number;

  private informacion = inject(InformacionService);
  private _servicioService = inject(ServiciosService);

  obtenerCatalogo(){
    this._servicioService.obtenerCatalogoServicio(this.idServicio).subscribe(
      {
        next: (response: Blob) => {
          const file = new Blob([response], { type: 'application/pdf' });
          const fileURL = URL.createObjectURL(file);
            
          window.open(fileURL, '_blank');
        },
        error: (error) => {
          this.informacion.informarError(error.error.mensaje || 'Error al obtener el catalogo, intente de nuevo');
        }
      }
    )
  }
}
