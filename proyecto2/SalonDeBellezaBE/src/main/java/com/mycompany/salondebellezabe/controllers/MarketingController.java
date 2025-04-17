/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.dtos.AnuncioDTO;
import com.mycompany.salondebellezabe.dtos.MensajeDTO;
import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.modelos.HistorialAnuncio;
import com.mycompany.salondebellezabe.service.anuncio.AnuncioService;
import com.mycompany.salondebellezabe.service.anuncio.PreciosAnuncioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author rafael-cayax
 */
@Path("marketing")
public class MarketingController {
    
    @Path("crear_anuncio")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publicarAnuncio(AnuncioDTO anuncio){
        AnuncioService servicio = new AnuncioService();
        MensajeDTO mensaje = servicio.publicarAnuncio(anuncio);
        return Response.ok(mensaje)
                .build();
    }
    
    @Path("crear_anuncio_imagen")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response publicarAnuncioImagen(
            @FormDataParam("imagen") FormDataBodyPart imagen,
            @FormDataParam("datos") String datosAnuncio
    ){
        AnuncioService servicio = new AnuncioService();
        MensajeDTO mensaje = servicio.publicarAnuncio(imagen, datosAnuncio);
        return Response.ok(mensaje)
                .build();
    }
    
    @Path("precios_anuncios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrecios(){
        PreciosAnuncioService precios = new PreciosAnuncioService();
        return Response.ok(precios.obtenerDatos())
                .build();
    }
    
    @Path("anuncio_imagen/{idAnuncio}")
    @GET
    public Response obtenerImagenAnuncio(
            @PathParam("idAnuncio") Integer idAnuncio){
        AnuncioService servicio = new AnuncioService();
        Fotografia foto = servicio.obtenerImagenFoto(idAnuncio);
        return Response.ok(foto.getFotografia())
                .header("Content-Type", foto.getExtension())
                .header("Content-Disposition", "inline; filename=\"imagen_anuncio_"+ idAnuncio + "\"")
                .build();
    }
    
    @Path("anuncio_vigentes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAnunciosVigentes(){
        AnuncioService servicio = new AnuncioService();
        return Response.ok(servicio.obtenerAnunciosParaMostrar())
                .build();
    }
    
    @Path("registro_anuncio")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarUsoAnuncio(HistorialAnuncio historial){
        return Response.ok()
                .build();
    }
}
