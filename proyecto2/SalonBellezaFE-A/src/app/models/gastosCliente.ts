import { Cita } from "./cita";
import { Usuario } from "./usuario";

export interface GastosCliente{
    cliente: Usuario;
    gastoTotal: number;
    citas: Cita[];
}