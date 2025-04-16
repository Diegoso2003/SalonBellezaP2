import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../../models/api_url';
import { Observable } from 'rxjs';
import { PreciosAnuncio } from '../../models/preciosAnuncio';
import { AnuncioDTO } from '../../DTO/anuncioDTO';
import { MensajeDTO } from '../../DTO/mensajeDTO';

@Injectable({
  providedIn: 'root'
})
export class MarketingService {
  private _http = inject(HttpClient);
  private url = `${API_URL}/marketing`;
  constructor() { }

  crearAnuncio(anuncio: AnuncioDTO): Observable<MensajeDTO> {
    return this._http.post<MensajeDTO>(`${this.url}/crear_anuncio`, anuncio);
  }

  crearAnuncioImagen(anuncio: FormData): Observable<MensajeDTO> {
    return this._http.post<MensajeDTO>(`${this.url}/crear_anuncio_imagen`, anuncio);
  }

  obtenerDatosAnuncio(): Observable<PreciosAnuncio>{
    return this._http.get<PreciosAnuncio>(`${this.url}/precios_anuncios`);
  }
}
