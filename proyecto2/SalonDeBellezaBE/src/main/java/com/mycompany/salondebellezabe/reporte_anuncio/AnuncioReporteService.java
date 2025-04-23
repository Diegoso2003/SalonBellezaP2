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
    
    public List<Anuncio> anunciosMasVistos(Consulta consulta) {
        return repositorio.anunciosMasMostrados(consulta);
    }

    public List<Anuncio> anunciosMenosVistos(Consulta consulta) {
        return repositorio.anunciosMenosMostrados(consulta);
    }
    
}
