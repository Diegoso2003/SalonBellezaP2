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
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Anuncio;
import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.modelos.PreciosAnuncio;
import com.mycompany.salondebellezabe.modelos.Vigencia;
import com.mycompany.salondebellezabe.modelos.enums.TipoAnuncio;
import com.mycompany.salondebellezabe.repositorio.anuncios.AnuncioDAO;
import com.mycompany.salondebellezabe.repositorio.anuncios.ImagenAnuncioDAO;
import com.mycompany.salondebellezabe.service.Service;
import com.mycompany.salondebellezabe.validador.anuncio.ValidadorAnuncio;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

/**
 *
 * @author rafael-cayax
 */
public class AnuncioService extends Service<Anuncio>{
    private final ValidadorAnuncio validadorAnuncio;
    private final AnuncioDAO anuncioDAO;
    
    public AnuncioService() {
        super(new AnuncioDAO(), new ValidadorAnuncio(), "anuncio no encontrado");
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
    
    /**
     * metodo para obtener los anuncios que se mostraran al inicio de la pantalla
     * @return 
     */
    public Anuncio obtenerAnuncioParaMostrar(){
        List<Anuncio> anunciosActivos = anuncioDAO.obtenerAnunciosActivos();
        validarVigenciaAnuncios(anunciosActivos);
        anunciosActivos = anuncioDAO.obtenerAnunciosActivos();
        if (anunciosActivos.size() == 1) {
            return anunciosActivos.getFirst();
        } else if(anunciosActivos.isEmpty()){
            throw new NotFoundException("no hay anuncios vigentes");
        }
        return seleccionarAnunciosAlAzar(anunciosActivos);
    }

    /**
     * metodo para validar que los anuncios sigan siendo vigentes
     * @param anunciosActivos los anuncios activos
     */
    private void validarVigenciaAnuncios(List<Anuncio> anunciosActivos) {
        LocalDate hoy = LocalDate.now();
        for(Anuncio anuncio: anunciosActivos){
            Vigencia vigencia = anuncio.getVigencia();
            long dias = ChronoUnit.DAYS.between(hoy, vigencia.getFechaPublicacion());
            if (dias > vigencia.getDias()) {
                anuncioDAO.eliminar(anuncio.getIdAnuncio());
            }
        }
    }

    /**
     * metodo para seleccionar aleatoriamente los anuncios que se mostran en la pantalla
     * @param anunciosActivos los anuncios que esten activos
     * @return los anuncios que se mostraran
     */
    private Anuncio seleccionarAnunciosAlAzar(List<Anuncio> anunciosActivos) {
        Random r = new Random();
        Integer indice = r.nextInt(0, anunciosActivos.size());
        return anunciosActivos.get(indice);
    }

    /**
     * 
     * @param idEnviado
     * @return 
     */
    public Fotografia obtenerImagenFoto(String idEnviado) {
        Integer idAnuncio = validador.validarId(idEnviado);
        ImagenAnuncioDAO imagen = new ImagenAnuncioDAO();
        Optional<Fotografia> posibleFoto = imagen.obtenerPorID(idAnuncio);
        return posibleFoto.orElseThrow(() -> new NotFoundException("no se encontro la imagen del anuncio"));
    }
}
