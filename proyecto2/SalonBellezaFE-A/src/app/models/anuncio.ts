import { TipoAnuncio } from "./enums/tipoAnuncio";
import { HistorialAnuncio } from "./historialAnuncio";
import { Vigencia } from "./vigencia";

export interface Anuncio{
    idAnuncio: number;
    texto: string;
    urlVideo: string;
    estado: boolean;
    tipo: TipoAnuncio;
    vigencia: Vigencia;
    historial: HistorialAnuncio[];
    totalMostrado: number;
}