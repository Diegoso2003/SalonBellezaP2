import { Rol } from "./enums/Rol";

export interface Usuario {
    dpi: string;
    nombre: string;
    correo: string;
    telefono: string;
    direccion: string;
    contraseña: string;
    confirmacionContraseña: string;
    rol: Rol;
    gustos: string;
    descripcion: string;
    hobbies: string;
    activo: boolean;
    listaNegra: boolean;
}
