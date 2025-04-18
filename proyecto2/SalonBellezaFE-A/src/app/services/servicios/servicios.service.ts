import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../../models/api_url';
import { Usuario } from '../../models/usuario';
import { Observable } from 'rxjs';
import { Servicio } from '../../models/servicio';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {

  private _http = inject(HttpClient);
  private url = `${API_URL}/servicios`;
  constructor() { }

  obtenerEmpleados(): Observable<Usuario[]> {
    return this._http.get<Usuario[]>(`${this.url}/empleados`);
  }

  obtenerInformacionEmpleado(dpi: string): Observable<Usuario> {
    return this._http.get<Usuario>(`${this.url}/empleado/${dpi}`);
  }

  crearServicio(servicio: FormData): Observable<any> {
    return this._http.post<any>(`${this.url}/crear`, servicio);
  }

  obtenerServiciosDisponibles(): Observable<Servicio[]> {
    return this._http.get<Servicio[]>(`${this.url}/servicios_disponibles`);
  }
}
