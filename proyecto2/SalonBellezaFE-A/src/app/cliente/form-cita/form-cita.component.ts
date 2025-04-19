import { Component, inject, OnInit } from '@angular/core';
import { VistaAnuncioComponent } from "../vista-anuncio/vista-anuncio.component";
import { ReactiveFormsModule } from '@angular/forms';
import { VistaServicioComponent } from "../../servicios/vista-servicio/vista-servicio.component";
import { Servicio } from '../../models/servicio';
import { InformacionComponent } from "../../informacion/informacion.component";
import { InformacionService } from '../../services/informacion.service';
import { ActivatedRoute } from '@angular/router';
import { ServiciosService } from '../../services/servicios/servicios.service';
import { FormCitaHijoComponent } from "../form-cita-hijo/form-cita-hijo.component";

@Component({
  selector: 'app-form-cita',
  standalone: true,
  imports: [VistaAnuncioComponent, ReactiveFormsModule, VistaServicioComponent, InformacionComponent, FormCitaHijoComponent],
  templateUrl: './form-cita.component.html',
  styleUrl: './form-cita.component.scss'
})
export class FormCitaComponent implements OnInit{
  servicio!: Servicio;
  servicioObtenido: boolean = false;

  private informacion = inject(InformacionService);
  private _router = inject(ActivatedRoute);
  private _servicioService = inject(ServiciosService);

  ngOnInit(): void {
    this.obtenerIdServicio();
  }

  private obtenerIdServicio(): void{
    const idServicio = this._router.snapshot.params['idServicio'];
    let idServicioNumber = Number(idServicio);
    console.log(idServicioNumber);
    if (isNaN(idServicioNumber)) {
      this.informacion.informarError('El id del servicio no es valido');
      return;
    }
    this.obtenerServicio(idServicioNumber);
  }

  private obtenerServicio(idServicioNumber: number): void{
    this._servicioService.obtenerServicio(idServicioNumber).subscribe({
      next: (servicio: Servicio) => {
        this.servicio = servicio;
        this.servicioObtenido = true;
      },
      error: (error) => {
        this.informacion.informarError( error.error.mensaje || 'Error al cargar el servicio intentar mas tarde');
        console.log(error);
      }
    });
  }
}
