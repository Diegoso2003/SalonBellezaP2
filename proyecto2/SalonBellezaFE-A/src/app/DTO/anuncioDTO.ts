import { TipoAnuncio } from "../models/enums/tipoAnuncio";

export interface AnuncioDTO {
    texto: string;
    urlVideo: string;
    tipo: TipoAnuncio;
    fechaPublicacion: Date;
    dias: number;
}