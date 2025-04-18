import { Component, Input } from '@angular/core';

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

  obtenerCatalogo(){
    
  }
}
