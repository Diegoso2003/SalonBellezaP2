import { Component, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-navbar-servicios',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './navbar-servicios.component.html',
  styleUrl: './navbar-servicios.component.scss'
})
export class NavbarServiciosComponent {
  private _usuarioService = inject(UsuarioService);


  cerrarSesion(){
    this._usuarioService.logout();
  }
}
