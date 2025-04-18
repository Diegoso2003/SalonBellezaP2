import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-imagen-perfil',
  standalone: true,
  imports: [],
  templateUrl: './imagen-perfil.component.html',
  styleUrl: './imagen-perfil.component.scss'
})
export class ImagenPerfilComponent implements OnInit, OnDestroy{

  @Input({required: true})
  dpi!: string;
  imagenUrl: SafeUrl | null = null;

  private _usuarioService = inject(UsuarioService);
  private _sanitizer = inject(DomSanitizer);

  ngOnInit(): void {
    this.obtenerImagen();
  }

  private obtenerImagen(): void {
    this._usuarioService.obtenerImagenPerfil(this.dpi).subscribe({
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
