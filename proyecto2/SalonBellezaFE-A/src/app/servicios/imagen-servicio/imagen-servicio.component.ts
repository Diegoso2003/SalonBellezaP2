import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-imagen-servicio',
  standalone: true,
  imports: [],
  templateUrl: './imagen-servicio.component.html',
  styleUrl: './imagen-servicio.component.scss'
})
export class ImagenServicioComponent implements OnInit{
  @Input({ required: true })
  idServicio!: number;

  ngOnInit(): void {
    this.obtenerImagenServicio();  
  }

  private obtenerImagenServicio(){

  }
}
