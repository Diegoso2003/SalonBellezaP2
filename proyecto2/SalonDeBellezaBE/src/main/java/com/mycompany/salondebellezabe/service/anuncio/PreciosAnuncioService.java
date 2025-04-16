/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.anuncio;

import com.mycompany.salondebellezabe.modelos.PreciosAnuncio;
import com.mycompany.salondebellezabe.repositorio.salon_belleza.PreciosAnuncioRep;
import com.mycompany.salondebellezabe.service.ServicioSalon;
import com.mycompany.salondebellezabe.validador.anuncio.ValidadorPreciosAnuncio;

/**
 *
 * @author rafael-cayax
 */
public class PreciosAnuncioService extends ServicioSalon<PreciosAnuncio>{

    public PreciosAnuncioService() {
        super(new PreciosAnuncioRep(), new ValidadorPreciosAnuncio());
    }

}
