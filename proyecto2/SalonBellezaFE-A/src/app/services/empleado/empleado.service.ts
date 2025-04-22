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
}
