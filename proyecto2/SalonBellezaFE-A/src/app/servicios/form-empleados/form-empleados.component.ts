import { Component, inject, Input } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { ServiciosService } from '../../services/servicios/servicios.service';
import { Informacion } from '../../models/informacion';
import { ImagenPerfilComponent } from "../../compartido/imagen-perfil/imagen-perfil.component";
import { ListaEmpleadosServicioComponent } from "../lista-empleados-servicio/lista-empleados-servicio.component";

@Component({
  selector: 'app-form-empleados',
  standalone: true,
  imports: [ImagenPerfilComponent, ListaEmpleadosServicioComponent],
  templateUrl: './form-empleados.component.html',
  styleUrl: './form-empleados.component.scss'
})
export class FormEmpleadosComponent{

  @Input({required: true})
  empleados!: Usuario[];
  @Input({required: true})
  informacion!: Informacion;
  @Input()
  empleadosDelServicio!: Usuario[];
  @Input({required: true})
  gestionarServicio: boolean = true;
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

  agregrarEmpleado(dpi: string): void{
    let encontrado = false;
    for(let empleado of this.empleados){
      if(empleado.dpi === dpi){
        encontrado = true;
        if(!this.empleadoYaAgregado(dpi)){
          this.empleadosDelServicio.push(empleado);
        }else{
          this.informacion.informarError('El empleado ya ha sido agregado');
        }
        break;
      }
    }
    if(!encontrado){
      this.informacion.informarError('Error al agregar el empleado');
    }
  }

  private empleadoYaAgregado(dpi: string): boolean{
    for(let empleado of this.empleadosDelServicio){
      if(empleado.dpi === dpi){
        return true;
      }
    }
    return false;
  }
}
