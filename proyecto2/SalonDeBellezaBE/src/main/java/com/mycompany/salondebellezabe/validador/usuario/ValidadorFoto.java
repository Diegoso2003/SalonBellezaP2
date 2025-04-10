/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.validador.usuario;

import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.validador.Validador;

/**
 *
 * @author rafael-cayax
 */
public class ValidadorFoto extends Validador<Fotografia>{
    private static final String[] IMAGENES_ACEPTADAS = {
        "image/png",
        "image/jpeg",
        "image/svg+xml",
        "image/webp"
    };
    
    @Override
    protected boolean esValido() {
        return esImagenValida();
    }

    @Override
    public void validarDatos(Fotografia foto) {
        this.entidad = foto;
        if (esValido()) {
            
        }
    }
    
    private boolean esImagenValida(){
        String extension = this.entidad.getExtension();
        for(String imagen: IMAGENES_ACEPTADAS){
            if (imagen.equals(extension)) {
                return true;
            }
        }
        return false;
    }
    
}
