import { Usuario } from "../models/usuario";

export class UsuarioSeleccionado{
    private usuarioSeleccionado?: Usuario;

    public setUsuarioSeleccionado(usuario: Usuario): void{
        this.usuarioSeleccionado = usuario;
    }

    public usuarioNulo(): boolean{
        return this.usuarioSeleccionado === undefined;
    }
    
    public getUsuarioSeleccionado(): Usuario{
        if(this.usuarioNulo()){
            throw new Error('El usuario seleccionado es nulo');
        }
        return this.usuarioSeleccionado!;
    }
}