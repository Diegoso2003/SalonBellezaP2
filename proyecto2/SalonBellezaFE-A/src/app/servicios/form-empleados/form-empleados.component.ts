import { Component, inject, Input, OnInit } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { ServiciosService } from '../../services/servicios/servicios.service';
import { Informacion } from '../../models/informacion';
import { ImagenPerfilComponent } from "../../compartido/imagen-perfil/imagen-perfil.component";

@Component({
  selector: 'app-form-empleados',
  standalone: true,
  imports: [ImagenPerfilComponent],
  templateUrl: './form-empleados.component.html',
  styleUrl: './form-empleados.component.scss'
})
export class FormEmpleadosComponent{

  @Input({required: true})
  empleados!: Usuario[];
  @Input({required: true})
  informacion!: Informacion;
  empleadoSeleccionado!: Usuario;
  mostrarInformacion: boolean = false;
  informacionObtenida: boolean = false;
  inputSelectTocado: boolean = false;
  private _servicioService = inject(ServiciosService);  

  usuarioSeleccionado(event: Event): void{
    this.inputSelectTocado = true;
    this.mostrarInformacion = true;
    const selectElement = event.target as HTMLSelectElement;
    const selectedValue: string = selectElement.value;
    this.informacionObtenida = false;
    this._servicioService.obtenerInformacionEmpleado(selectedValue).subscribe(
      {
        next: (empleado: Usuario) => {
          this.empleadoSeleccionado = empleado;
          this.informacionObtenida = true;
        },
        error: (error) => {
          this.informacionObtenida = false;
          console.error('Error al obtener la información del empleado:', error);
          this.informacion.informarError('Error al obtener la información del empleado');
        }
      }
    );
  }
}
