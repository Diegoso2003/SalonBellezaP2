import { EstadoCita } from "./enums/estadoCita";
import { Servicio } from "./servicio";
import { Usuario } from "./usuario";

export interface Cita{
    idCita: number;
    empleado: Usuario;
    cliente: Usuario;
    fecha: Date;
    hora: string;
    horaFin: string;
    costoTotal: number;
    estado: EstadoCita;
    servicio: Servicio;
}