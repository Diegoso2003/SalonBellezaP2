import { Cita } from "./cita";
import { Servicio } from "./servicio";

export interface ReporteServicioCitas {
    servicio: Servicio;
    citas: Cita[];
    totalCitas: number;
}