import { Component, inject, OnInit } from '@angular/core';
import { Servicio } from '../../models/servicio';
import { ServiciosService } from '../../services/servicios/servicios.service';

@Component({
  selector: 'app-servicios-disponibles',
  standalone: true,
  imports: [],
  templateUrl: './servicios-disponibles.component.html',
  styleUrl: './servicios-disponibles.component.scss'
})
export class ServiciosDisponiblesComponent implements OnInit{
  anunciosDisponibles: boolean = false;
  serviciosDisponibles!: Servicio[];
  private _servicioService = inject(ServiciosService);

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
          console.log(error);
        }
      }
    );
  }
}
