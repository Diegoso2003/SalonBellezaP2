/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.reporte_anuncio;

import com.mycompany.salondebellezabe.consulta_reportes.Consulta;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteAnuncio;
import com.mycompany.salondebellezabe.modelos.Anuncio;
import java.util.List;

/**
 *
 * @author rafael-cayax
 */
public class AnuncioReporteService {

    private final ReporteAnuncio repositorio;

    public AnuncioReporteService() {
        repositorio = new ReporteAnuncio();
    }
    
    /**
     * metodo usado para obtener los anuncios mas vistos
     * @param consulta los datos para la consulta
     * @return una lista con los anuncios
     */
    public List<Anuncio> anunciosMasVistos(Consulta consulta) {
        return repositorio.anunciosMasMostrados(consulta);
    }

    /**
     * metodo usado para obtener los anuncios menos vistos
     * @param consulta los datos para la consulta
     * @return una lista con los anuncios
     */
    public List<Anuncio> anunciosMenosVistos(Consulta consulta) {
        return repositorio.anunciosMenosMostrados(consulta);
    }
    
}
