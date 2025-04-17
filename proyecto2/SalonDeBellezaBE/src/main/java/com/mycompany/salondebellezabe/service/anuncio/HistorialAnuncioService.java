/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.anuncio;

import com.mycompany.salondebellezabe.modelos.HistorialAnuncio;
import com.mycompany.salondebellezabe.repositorio.anuncios.HistorialAnuncioDAO;
import com.mycompany.salondebellezabe.service.Service;
import com.mycompany.salondebellezabe.validador.anuncio.ValidadorHistorial;

/**
 *
 * @author rafael-cayax
 */
public class HistorialAnuncioService extends Service<HistorialAnuncio>{
    
    public HistorialAnuncioService() {
        super(new HistorialAnuncioDAO(), new ValidadorHistorial());
    }
    
}
