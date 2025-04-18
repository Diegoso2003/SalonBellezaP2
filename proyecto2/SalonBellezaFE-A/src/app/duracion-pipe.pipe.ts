import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'duracionPipe',
  standalone: true
})
export class DuracionPipePipe implements PipeTransform {

  transform(value: string): string {
    const partes = value.split(':');
    let horas = parseInt(partes[0]);
    let minutos = parseInt(partes[1]);
    let cadena: string = '';

    if (horas > 0){
      if (horas > 1) {
        cadena += horas + ' horas';
      }
      else {
        cadena += horas + ' hora';
      }
    }
    if (minutos > 0){
      if (horas > 0){
        cadena += ' y ';
      }
      if (minutos > 1) {
        cadena += minutos + ' minutos';
      }
      else {
        cadena += minutos + ' minuto';
      }
    }
    return cadena;
  }

}
