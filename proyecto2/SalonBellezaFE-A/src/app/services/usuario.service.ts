import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { API_URL } from '../models/api_url';
import { Usuario } from '../models/usuario';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private tokenKey: string = 'auth_token';
  private _http = inject(HttpClient);
  private url = `${API_URL}/usuario`
  private router = inject(Router);
  constructor() { }

  public redireccionarUsuario(usuario: Usuario): void {
    this.setToken(usuario.dpi);
    switch (usuario.rol) {
      case 'ADMINISTRADOR':
        this.router.navigate(['/admin']);
        break;
      case 'CLIENTE':
        if(usuario.activo === false){
          this.router.navigate(['/detalles']);
          return;
        }
        this.router.navigate(['/cliente']);
        break;
      case 'EMPLEADO':
        this.router.navigate(['/repartidor']);
        break;
      case 'MARKETING':
        this.router.navigate(['/marketing']);
        break;
      case 'SERVICIOS':
        this.router.navigate(['/servicios']);
        break;
    } 
  }

  private setToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  login(usuario: Usuario): Observable<Usuario> {
    return this._http.post<Usuario>(`${this.url}/login`, usuario);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login']);
  }

  registrarUsuario(usuario: Usuario): Observable<any> {
    return this._http.post<Usuario>(`${this.url}/registro_usuario`, usuario);
  }

  registrarEmpleado(formData: FormData): Observable<void> {
    return this._http.post<void>(`${this.url}/registro_empleado`, formData);
  }

  obtenerImagenPerfil(dpi: string): Observable<Blob> {
    return this._http.get(`${this.url}/imagen_perfil/${dpi}`, { responseType: 'blob' });
  }
}
