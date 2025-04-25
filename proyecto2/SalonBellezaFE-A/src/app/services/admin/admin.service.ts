import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../../models/api_url';
import { Consulta } from '../../DTO/consulta';
import { Observable } from 'rxjs';
import { GastosCliente } from '../../models/gastosCliente';

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
}
