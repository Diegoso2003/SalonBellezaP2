import { Component, inject, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-boton-agendar',
  standalone: true,
  imports: [],
  templateUrl: './boton-agendar.component.html',
  styleUrl: './boton-agendar.component.scss'
})
export class BotonAgendarComponent {

  @Input({ required: true })
  idServicio!: number;
  private router = inject(Router);
  
  agendarCita(){
    console.log('Agendar cita para el servicio con ID:', this.idServicio);
    this.router.navigate([`/cliente/agendar_cita/${this.idServicio}`]);
  }
}
