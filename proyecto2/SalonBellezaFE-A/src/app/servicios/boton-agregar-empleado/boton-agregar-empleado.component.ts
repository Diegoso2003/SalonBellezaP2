import { Component, inject, Input } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { InformacionService } from '../../services/informacion.service';
import { UsuarioSeleccionado } from '../../class/usuarioSeleccionado';

@Component({
  selector: 'app-boton-agregar-empleado',
  standalone: true,
  imports: [],
  templateUrl: './boton-agregar-empleado.component.html',
  styleUrl: './boton-agregar-empleado.component.scss'
})
export class BotonAgregarEmpleadoComponent {

  @Input({ required: true })
  empleadosDisponibles!: Usuario[];
  @Input({ required: true })
  empleadosDelServicio!: Usuario[];
  @Input({ required: true })
  actualizarServicio!: boolean;
  @Input({ required: true })
  usuarioSeleccionado!: UsuarioSeleccionado;

  private informacion = inject(InformacionService);

  agregrarEmpleado(): void{
    const empleadoSeleccionado = this.usuarioSeleccionado.getUsuarioSeleccionado();
    const dpi = empleadoSeleccionado.dpi;
    if(!this.actualizarServicio){
      this.agregarAlaLista(dpi);
    } else {
      this.agregarEmpleadoAlServicio(dpi);
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

  private agregarAlaLista(dpi: string): void{
    let encontrado = false;
    for(let empleado of this.empleadosDisponibles){
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

  private agregarEmpleadoAlServicio(dpi: string): void{
  
  }
}
