import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ServiciosService } from '../../services/servicios/servicios.service';

@Component({
  selector: 'app-imagen-servicio',
  standalone: true,
  imports: [],
  templateUrl: './imagen-servicio.component.html',
  styleUrl: './imagen-servicio.component.scss'
})
export class ImagenServicioComponent implements OnInit, OnDestroy{
  @Input({ required: true })
  idServicio!: number;

  imagenUrl: SafeUrl | null = null;
  private _servicioService = inject(ServiciosService);
  private _sanitizer = inject(DomSanitizer);
  ngOnInit(): void {
    this.obtenerImagenServicio();  
  }

  private obtenerImagenServicio(){
    this._servicioService.obtenerImagenServicio(this.idServicio).subscribe({
      next: (imagen: Blob) => {
        const url = URL.createObjectURL(imagen);
        this.imagenUrl = this._sanitizer.bypassSecurityTrustUrl(url);
      },
      error: (error) => {
        console.error('Error al obtener la imagen de perfil:', error);
        this.imagenUrl = null;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.imagenUrl) {
      URL.revokeObjectURL(this.imagenUrl as unknown as string);
    }
  }
}
