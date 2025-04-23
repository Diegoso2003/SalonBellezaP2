import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../../models/api_url';
import { CitasEmpleadoDiaDTO } from '../../DTO/CitasEmpleadoDiaDTO';
import { Cita } from '../../models/cita';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {
  private _http = inject(HttpClient);
  private url = `${API_URL}/empleado`;
  constructor() { }

  obtenerAgenda(consulta: CitasEmpleadoDiaDTO): Observable<Cita[]> {
    return this._http.post<Cita[]>(`${this.url}/agenda`, consulta);
  }

  marcarCitaAtendida(idCita: number): Observable<void> {
    return this._http.patch<void>(`${this.url}/cita_atendida/${idCita}`, {});
  }

  marcarClienteAusente(idCita: number): Observable<void> {
    return this._http.patch<void>(`${this.url}/cita_ausente/${idCita}`, {});
  }
}
