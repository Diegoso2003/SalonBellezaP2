import { Component, Input } from '@angular/core';

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

  agendarCita(){

  }
}
