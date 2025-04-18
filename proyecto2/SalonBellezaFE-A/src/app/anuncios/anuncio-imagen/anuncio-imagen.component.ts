import { Component, inject, Input, OnInit } from '@angular/core';
import { MarketingService } from '../../services/marketing/marketing.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

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
imagenUrl: SafeUrl | null = null;

  private _usuarioService = inject(MarketingService);
  private _sanitizer = inject(DomSanitizer);

  ngOnInit(): void {
    this.obtenerImagen();
  }

  private obtenerImagen(): void {
    this._usuarioService.obtenerImagenAnuncio(this.anuncioId).subscribe({
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
