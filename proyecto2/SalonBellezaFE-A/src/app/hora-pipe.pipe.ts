import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'horaPipe',
  standalone: true
})
export class HoraPipePipe implements PipeTransform {

  transform(value: string): string {
    const [horas, minutos] = value.split(':');
    const horasInt = parseInt(horas);
    const minutosInt = parseInt(minutos);
    const ampm = horasInt >= 12 ? 'PM' : 'AM';
    let mensaje = '';
    if (horasInt > 12) {
      mensaje = `${horasInt - 12}:${minutos} ${ampm}`;
    } else {
      mensaje = `${horasInt}:${minutos} ${ampm}`;
    }
    return mensaje;
  }

}
