import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../../models/api_url';
import { Consulta } from '../../DTO/consulta';
import { Observable } from 'rxjs';
import { GastosCliente } from '../../models/gastosCliente';
import { CitasCliente } from '../../models/citasCliente';
import { ServicioGanancias } from '../../models/servicioGanancias';
import { EmpleadoGanancias } from '../../models/empleadoGanancias';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private _http = inject(HttpClient);
  private url = `${API_URL}/admin`
  constructor() { }

  obtenerClienteMasGasto(consulta: Consulta): Observable<GastosCliente[]> {
    return this._http.post<GastosCliente[]>(`${this.url}/reporte_cliente_mayor_gasto`, consulta);
  }

  obtenerClienteMenorGasto(consulta: Consulta): Observable<GastosCliente[]>{
    return this._http.post<GastosCliente[]>(`${this.url}/reporte_cliente_menor_gasto`, consulta);
  }

  obtenerClienteMasCitas(consulta: Consulta): Observable<CitasCliente[]> {
    return this._http.post<CitasCliente[]>(`${this.url}/reporte_cliente_mas_citas`, consulta);
  }

  obtenerClienteMenosCitas(consulta: Consulta): Observable<CitasCliente[]> {
    return this._http.post<CitasCliente[]>(`${this.url}/reporte_cliente_menos_citas`, consulta);
  }

  obtenerServiciosGanancia(consulta: Consulta): Observable<ServicioGanancias[]> {
    return this._http.post<ServicioGanancias[]>(`${this.url}/reporte_servicios_ganancias`, consulta);
  }

  obtenerGananciasPorEmpleado(consulta: Consulta): Observable<EmpleadoGanancias[]> {
    return this._http.post<EmpleadoGanancias[]>(`${this.url}/reporte_ganancias_empleado`, consulta);
  }

  obtenerCitasPorEmpleado(consulta: Consulta): Observable<EmpleadoGanancias[]> {
    return this._http.post<EmpleadoGanancias[]>(`${this.url}/reporte_citas_empleado`, consulta);
  }
}
