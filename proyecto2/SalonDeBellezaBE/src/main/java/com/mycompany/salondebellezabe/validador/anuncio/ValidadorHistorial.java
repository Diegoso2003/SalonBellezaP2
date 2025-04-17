/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.validador.anuncio;

import com.mycompany.salondebellezabe.modelos.HistorialAnuncio;
import com.mycompany.salondebellezabe.validador.Validador;

/**
 *
 * @author rafael-cayax
 */
public class ValidadorHistorial extends Validador<HistorialAnuncio>{
    private static final Integer URL_MAS_LARGA = 250;
    private static final Integer URL_MAS_CORTA = 15;
    
    @Override
    protected boolean esValido() {
        return esUrlValida() && esFechaValida() && esIdValido();
    }

    @Override
    public void validarDatos(HistorialAnuncio historial) {
        this.entidad = historial;
    }

    @Override
    public void validarActualizacion(HistorialAnuncio entidad) {

    }
    
    private boolean esUrlValida(){
        String url = this.entidad.getUrl();
        return this.cumpleRangoSinReemplazo(url, URL_MAS_CORTA, URL_MAS_LARGA);
    }
    
    private boolean esFechaValida(){
        return this.entidad.getFechaPublicacion() != null;
    }
    
    private boolean esIdValido(){
        return this.entidad.getIdAnuncio() != null;
    }
    
}
