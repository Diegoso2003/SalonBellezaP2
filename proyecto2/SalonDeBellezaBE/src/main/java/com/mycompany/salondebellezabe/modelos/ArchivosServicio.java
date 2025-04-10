/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.modelos;

import java.io.InputStream;

/**
 *
 * @author rafael-cayax
 */
public class ArchivosServicio {
    private Integer idArchivos;
    private InputStream catalogo;
    private byte[] catalogoBytes;
    private Fotografia foto;

    public Fotografia getFoto() {
        return foto;
    }

    public void setFoto(Fotografia foto) {
        this.foto = foto;
    }
    
    public Integer getIdArchivos() {
        return idArchivos;
    }

    public void setIdArchivos(Integer idArchivos) {
        this.idArchivos = idArchivos;
    }

    public InputStream getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(InputStream catalogo) {
        this.catalogo = catalogo;
    }

    public byte[] getCatalogoBytes() {
        return catalogoBytes;
    }

    public void setCatalogoBytes(byte[] catalogoBytes) {
        this.catalogoBytes = catalogoBytes;
    }
    
}
