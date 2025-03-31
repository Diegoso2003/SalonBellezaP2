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
    private InputStream fotografia;
    private byte[] foto;
    private InputStream catalogo;
    private byte[] catalogoBytes;
    private String extension;

    public Integer getIdArchivos() {
        return idArchivos;
    }

    public void setIdArchivos(Integer idArchivos) {
        this.idArchivos = idArchivos;
    }

    public InputStream getFotografia() {
        return fotografia;
    }

    public void setFotografia(InputStream fotografia) {
        this.fotografia = fotografia;
    }

    public InputStream getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(InputStream catalogo) {
        this.catalogo = catalogo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public byte[] getCatalogoBytes() {
        return catalogoBytes;
    }

    public void setCatalogoBytes(byte[] catalogoBytes) {
        this.catalogoBytes = catalogoBytes;
    }
    
}
