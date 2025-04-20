import { Usuario } from "../models/usuario";

export class UsuarioSeleccionado{
    private usuarioSeleccionado?: Usuario;
    private fechaSeleccionada: string | null = null;
    private usuarioEncontrado: boolean = false;

    public setUsuarioSeleccionado(usuario: Usuario | undefined): void{
        this.usuarioSeleccionado = usuario;
    }

    public usuarioNulo(): boolean{
        return this.usuarioSeleccionado === undefined;
    }

    public setFechaSeleccionada(fecha: string | null): void{
        this.fechaSeleccionada = fecha;
    }

    public getUsuarioSeleccionado(): Usuario{
        return this.usuarioSeleccionado!;
    }

    public esFechaValida(): boolean{
        return this.fechaSeleccionada !== null;
    }

    public getFechaSeleccionada(): string{
        return this.fechaSeleccionada!;
    }

    public sonAmbosValidos(): boolean{
        return this.usuarioSeleccionado !== undefined && this.fechaSeleccionada !== null;
    }

    public setUsuarioEncontrado(encontrado: boolean): void{
        this.usuarioEncontrado = encontrado;
    }

    public getUsuarioEncontrado(): boolean{
        return this.usuarioEncontrado;
    }
}