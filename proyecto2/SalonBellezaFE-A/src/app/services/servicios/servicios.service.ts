import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../../models/api_url';
import { Usuario } from '../../models/usuario';
import { Observable } from 'rxjs';
import { Servicio } from '../../models/servicio';
import { Consulta } from '../../DTO/consulta';
import { ReporteServicioCitas } from '../../models/reporteServicioCitas';
import { ServicioGanancias } from '../../models/servicioGanancias';

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

  obtenerImagenServicio(idServicio: number): Observable<Blob> {
    return this._http.get(`${this.url}/imagen_servicio/${idServicio}`, { responseType: 'blob'})
  }

  obtenerServicio(idServicio: number): Observable<Servicio> {
    return this._http.get<Servicio>(`${this.url}/${idServicio}`);
  }

  obtenerCatalogoServicio(idServicio: number): Observable<Blob> {
    return this._http.get(`${this.url}/catalogo_servicio/${idServicio}`, { responseType: 'blob'});
  }

  serviciosMasReservados(consulta: Consulta): Observable<ReporteServicioCitas[]> {
    return this._http.post<ReporteServicioCitas[]>(`${this.url}/servicios_mas_reservados`, consulta);
  }

  serviciosMenosReservados(consulta: Consulta): Observable<ReporteServicioCitas[]> {
    return this._http.post<ReporteServicioCitas[]>(`${this.url}/servicios_menos_reservados`, consulta);
  }

  servicioMayorGanancia(consulta: Consulta): Observable<ServicioGanancias> {
    return this._http.post<ServicioGanancias>(`${this.url}/servicio_mayor_ganancia`, consulta);
  }
}
