/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.validador.usuario;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
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
        "image/webp",
        "image/avif"
    };
    
    @Override
    protected boolean esValido() {
        return this.entidad.getFoto() != null && esImagenValida();
    }

    @Override
    public void validarDatos(Fotografia foto) {
        this.entidad = foto;
        if (!esValido()) {
            throw new InvalidDataException("ingresar una fotografia valida");
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

    @Override
    public void validarActualizacion(Fotografia entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
