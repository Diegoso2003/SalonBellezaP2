import { EstadoCita } from "./enums/estadoCita";
import { Usuario } from "./usuario";

export interface Cita{
    idCita: number;
    empleado: Usuario;
    cliente: Usuario;
    fecha: Date;
    hora: string;
    costoTotal: number;
    estado: EstadoCita;
}