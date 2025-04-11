import { Component, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-navbar-admin',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './navbar-admin.component.html',
  styleUrl: './navbar-admin.component.scss'
})
export class NavbarAdminComponent {
  private _usuarioService = inject(UsuarioService);

  cerrarSesion(){
    this._usuarioService.logout();
  }
}
