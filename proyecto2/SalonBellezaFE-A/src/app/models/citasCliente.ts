import { Cita } from "./cita";
import { Usuario } from "./usuario";

export interface CitasCliente {
    cliente: Usuario;
    citas: Cita[];
    totalCitas: number;
}