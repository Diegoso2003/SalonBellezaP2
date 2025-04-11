import { Component, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-navbar-marketing',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './navbar-marketing.component.html',
  styleUrl: './navbar-marketing.component.scss'
})
export class NavbarMarketingComponent {
  private _usuarioService = inject(UsuarioService);

  cerrarSesion() {
    this._usuarioService.logout();
  }
}
