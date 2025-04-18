import { Component, inject, OnInit } from '@angular/core';
import { Servicio } from '../../models/servicio';
import { ServiciosService } from '../../services/servicios/servicios.service';
import { InformacionComponent } from "../../informacion/informacion.component";
import { InformacionService } from '../../services/informacion.service';
import { VistaAnuncioComponent } from "../vista-anuncio/vista-anuncio.component";
import { VistaServicioComponent } from "../../servicios/vista-servicio/vista-servicio.component";

@Component({
  selector: 'app-servicios-disponibles',
  standalone: true,
  imports: [InformacionComponent, VistaAnuncioComponent, VistaServicioComponent],
  templateUrl: './servicios-disponibles.component.html',
  styleUrl: './servicios-disponibles.component.scss'
})
export class ServiciosDisponiblesComponent implements OnInit{
  serviciosDisponibles: Servicio[] = [];
  private _servicioService = inject(ServiciosService);
  private informacion = inject(InformacionService);

  ngOnInit(): void {
    this.obtenerServiciosDisponibles();
  }

  private obtenerServiciosDisponibles(){
    this._servicioService.obtenerServiciosDisponibles().subscribe(
      {
        next: (servicios: Servicio[]) => {
          this.serviciosDisponibles = servicios;
        },
        error: (error) => {
          this.informacion.informarError('Error al cargar los servicios intentar mas tarde');
          console.log(error);
        }
      }
    );
  }
}
