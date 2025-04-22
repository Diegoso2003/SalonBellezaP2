import { Component, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-navbar-empleado',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './navbar-empleado.component.html',
  styleUrl: './navbar-empleado.component.scss'
})
export class NavbarEmpleadoComponent {

  private _usuarioService = inject(UsuarioService);

  cerrarSesion(){
    this._usuarioService.logout();
  }
}
