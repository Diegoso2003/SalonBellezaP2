import { Usuario } from "./usuario";

export interface Servicio {
    idServicio: number;
    nombreServicio: string;
    descripcion: string;
    precio: number;
    duracion: string;
    activo: boolean;
    empleados: Usuario[];
}