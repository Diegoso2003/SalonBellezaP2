import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../../models/api_url';
import { Observable } from 'rxjs';
import { CitasEmpleadoDiaDTO } from '../../DTO/CitasEmpleadoDiaDTO';
import { HorarioEmpleadoDTO } from '../../DTO/horarioEmpleadoDTO';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private _http = inject(HttpClient);
  private url = `${API_URL}/cliente`;
  constructor() { }

  añadirDetalles(formData: FormData): Observable<any> {
    return this._http.post<any>(`${this.url}/detalles`, formData);
  }

  obtenerHorarioEmpleado(consulta: CitasEmpleadoDiaDTO): Observable<HorarioEmpleadoDTO[]> {
    return this._http.get<HorarioEmpleadoDTO[]>(`${this.url}/obtener_horario_empleado/${consulta.dpi}/${consulta.fecha}`);
  }
}
