import { Cita } from "./cita";
import { Servicio } from "./servicio";

export interface ServicioGanancias {
    servicio: Servicio;
    citas: Cita[];
    totalGanancias: number;
}