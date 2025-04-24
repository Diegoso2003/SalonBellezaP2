import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../../models/api_url';
import { Observable } from 'rxjs';
import { PreciosAnuncio } from '../../models/preciosAnuncio';
import { AnuncioDTO } from '../../DTO/anuncioDTO';
import { MensajeDTO } from '../../DTO/mensajeDTO';
import { HistorialAnuncio } from '../../models/historialAnuncio';
import { Anuncio } from '../../models/anuncio';
import { Consulta } from '../../DTO/consulta';

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

  obtenerImagenAnuncio(id: number): Observable<Blob> {
    return this._http.get(`${this.url}/anuncio_imagen/${id}`, { responseType: 'blob' });
  }

  reporteAnuncio(historial: HistorialAnuncio): Observable<void> {
    return this._http.post<void>(`${this.url}/registro_anuncio`, historial);
  }

  obtenerAnuncioQueSeMostrara(): Observable<Anuncio>{
    return this._http.get<Anuncio>(`${this.url}/anuncios_vigentes`);
  }

  anunciosMasVistos(consulta: Consulta): Observable<Anuncio[]> {
    return this._http.post<Anuncio[]>(`${this.url}/anuncios_mas_vistos`, consulta);
  }

  anunciosMenosVistos(consulta: Consulta): Observable<Anuncio[]> {
    return this._http.post<Anuncio[]>(`${this.url}/anuncios_menos_vistos`, consulta);
  }
}
