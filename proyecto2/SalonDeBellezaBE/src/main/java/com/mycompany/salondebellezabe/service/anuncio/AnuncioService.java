/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.anuncio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.salondebellezabe.dtos.AnuncioDTO;
import com.mycompany.salondebellezabe.dtos.MensajeDTO;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.Anuncio;
import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.modelos.PreciosAnuncio;
import com.mycompany.salondebellezabe.modelos.Vigencia;
import com.mycompany.salondebellezabe.modelos.enums.TipoAnuncio;
import com.mycompany.salondebellezabe.repositorio.anuncios.AnuncioDAO;
import com.mycompany.salondebellezabe.service.Service;
import com.mycompany.salondebellezabe.validador.anuncio.ValidadorAnuncio;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

/**
 *
 * @author rafael-cayax
 */
public class AnuncioService extends Service<Anuncio>{
    private final ValidadorAnuncio validadorAnuncio;
    private final AnuncioDAO anuncioDAO;
    
    public AnuncioService() {
        super(new AnuncioDAO(), new ValidadorAnuncio());
        this.validadorAnuncio = (ValidadorAnuncio) validador;
        this.anuncioDAO = (AnuncioDAO) repositorio; 
    }

    public MensajeDTO publicarAnuncio(AnuncioDTO anuncioDTO) {
        Anuncio anuncio = obtenerInfoAnuncio(anuncioDTO);
        obtenerPrecio(anuncio);
        this.crearEntidad(anuncio);
        return armarMensaje(anuncio);
    }

    /**
     * metodo para publicar un anuncio de imagen y texto
     * @param imagen la imagen del anuncio
     * @param datosAnuncio los datos del anuncio en formato json
     * @return un mensaje con datos
     */
    public MensajeDTO publicarAnuncio(FormDataBodyPart imagen, String datosAnuncio) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            AnuncioDTO anuncioDTO = mapper.readValue(datosAnuncio, AnuncioDTO.class);
            Anuncio anuncio = obtenerInfoAnuncio(anuncioDTO);
            obtenerImagenAnuncio(imagen, anuncio);
            obtenerPrecio(anuncio);
            this.crearEntidad(anuncio);
            return armarMensaje(anuncio);
        } catch (JsonProcessingException ex) {
            throw new InvalidDataException("ingresar correctamente los datos solicitados");
        }
    }

    /**
     * obtiente los datos del anuncio en formato json y lo transforma en un objeto
     * Anuncio
     * @param anuncioDTO los datos de la publicacion
     * @return un anuncio
     */
    private Anuncio obtenerInfoAnuncio(AnuncioDTO anuncioDTO) {
        Anuncio anuncio = new Anuncio();
        anuncio.setTexto(anuncioDTO.getTexto());
        anuncio.setTipo(anuncioDTO.getTipo());
        anuncio.setUrlVideo(anuncioDTO.getUrlVideo());
        Vigencia vigencia = new Vigencia();
        vigencia.setDias(anuncioDTO.getDias());
        vigencia.setFechaPublicacion(anuncioDTO.getFechaPublicacion());
        anuncio.setVigencia(vigencia);
        return anuncio;
    }

    /**
     * metodo para obtener la imagen del anuncio de los datos enviados
     * @param imagen la imagen del anuncio
     * @param anuncio el anuncio
     */
    private void obtenerImagenAnuncio(FormDataBodyPart imagen, Anuncio anuncio) {
        Fotografia foto = new Fotografia();
        if (imagen != null) {
            foto.setFoto(imagen.getContent());
            foto.setExtension(imagen.getMediaType().toString());
        }
        anuncio.setFoto(foto);
    }

    private void obtenerPrecio(Anuncio anuncio) {
        PreciosAnuncioService preciosServicio = new PreciosAnuncioService();
        PreciosAnuncio precios = preciosServicio.obtenerDatos();
        TipoAnuncio tipo = anuncio.getTipo();
        if (tipo != null) {
            double precio = obtenerPrecioTipo(precios, tipo);
            if (anuncio.getVigencia() != null & anuncio.getVigencia().getDias() != null) {
                Integer dias = anuncio.getVigencia().getDias();
                anuncio.getVigencia().setPrecio((precio * dias));
            }
        }
    }

    private double obtenerPrecioTipo(PreciosAnuncio precios, TipoAnuncio tipo) {
        switch(tipo){
            case IMAGEN:
                return precios.getImagen();
            case TEXTO:
                return precios.getTexto();
            default:
                return precios.getVideo();
        }
    }

    private MensajeDTO armarMensaje(Anuncio anuncio) {
        MensajeDTO mensaje = new MensajeDTO();
        mensaje.setMensaje("anuncio creado con el precio de: Q" + anuncio.getVigencia().getPrecio() + "");
        return mensaje;
    }
    
}
