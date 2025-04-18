import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-anuncio-imagen',
  standalone: true,
  imports: [],
  templateUrl: './anuncio-imagen.component.html',
  styleUrl: './anuncio-imagen.component.scss'
})
export class AnuncioImagenComponent implements OnInit{

  @Input({ required: true })
  anuncioId!: number;

  ngOnInit(): void {
      
  }
}
