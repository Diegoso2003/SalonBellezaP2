import { Component, inject, Input } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { ServiciosService } from '../../services/servicios/servicios.service';
import { ImagenPerfilComponent } from "../../compartido/imagen-perfil/imagen-perfil.component";
import { InformacionService } from '../../services/informacion.service';
import { UsuarioSeleccionado } from '../../class/usuarioSeleccionado';

@Component({
  selector: 'app-form-empleados',
  standalone: true,
  imports: [ImagenPerfilComponent],
  templateUrl: './form-empleados.component.html',
  styleUrl: './form-empleados.component.scss'
})
export class FormEmpleadosComponent{

  @Input({required: true})
  empleadosDisponibles!: Usuario[];
  @Input({required: true})
  seleccionado!: UsuarioSeleccionado;
  mostrarInformacion: boolean = false;
  empleadoSeleccionado!: Usuario;
  inputSelectTocado: boolean = false;
  private _servicioService = inject(ServiciosService);  
  private informacion = inject(InformacionService);

  usuarioSeleccionado(event: Event): void{
    this.mostrarInformacion = true;
    const selectElement = event.target as HTMLSelectElement;
    const selectedValue: string = selectElement.value;
    this.seleccionado.setUsuarioEncontrado(false);
    this._servicioService.obtenerInformacionEmpleado(selectedValue).subscribe(
      {
        next: (empleado: Usuario) => {
          this.seleccionado.setUsuarioSeleccionado(empleado);
          this.empleadoSeleccionado = empleado;
          this.seleccionado.setUsuarioEncontrado(true);
        },
        error: (error) => {
          this.seleccionado.setUsuarioEncontrado(false);
          console.error('Error al obtener la información del empleado:', error);
          this.informacion.informarError('Error al obtener la información del empleado');
        }
      }
    );
  }
}
