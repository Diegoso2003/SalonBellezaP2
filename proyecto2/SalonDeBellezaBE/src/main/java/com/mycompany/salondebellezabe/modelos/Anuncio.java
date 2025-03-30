/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.modelos;

import com.mycompany.salondebellezabe.modelos.enums.TipoAnuncio;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author rafael-cayax
 */
public class Anuncio {
    private Integer idAnuncio;
    private TipoAnuncio tipo;
    private String texto;
    private String urlVideo;
    private InputStream foto;
    private boolean estado;
    private List<HistorialAnuncio> historial;
    private Vigencia vigencia;

    public Vigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }
    
    public List<HistorialAnuncio> getHistorial() {
        return historial;
    }

    public void setHistorial(List<HistorialAnuncio> historial) {
        this.historial = historial;
    }
    
    public Integer getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(Integer idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public TipoAnuncio getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnuncio tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public InputStream getFoto() {
        return foto;
    }

    public void setFoto(InputStream foto) {
        this.foto = foto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}
