import { Component, inject } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-navbar-cliente',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './navbar-cliente.component.html',
  styleUrl: './navbar-cliente.component.scss'
})
export class NavbarClienteComponent {

  private _usuarioService = inject(UsuarioService);

  cerrarSesion(){
    this._usuarioService.logout();
  }
}
