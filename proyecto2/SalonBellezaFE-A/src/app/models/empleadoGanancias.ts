import { Cita } from "./cita";
import { Usuario } from "./usuario";

export interface EmpleadoGanancias {
    empleado: Usuario;
    totalGanancias: number;
    totalCitas: number;
    citas: Cita[];
}