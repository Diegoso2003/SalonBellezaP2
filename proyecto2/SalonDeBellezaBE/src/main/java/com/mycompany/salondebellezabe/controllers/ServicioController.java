/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.service.servicio.ServicioService;
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
@Path("servicios")
public class ServicioController {
    
    @Path("empleados")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpleadosActivos(){
        ServicioService servicio = new ServicioService();
        return Response.ok(servicio.obtenerEmpleados())
                .build();
    }
    
    @Path("empleado/{dpi}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerInformacionEmpleado(@PathParam("dpi") String dpi){
        ServicioService servicio = new ServicioService();
        return Response.ok(servicio.obtenerInformacionEmpleado(dpi))
                .build();
    }
    
    @Path("crear")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response crearServicio(
            @FormDataParam("foto") FormDataBodyPart fotoServicio,
            @FormDataParam("catalogo") FormDataBodyPart catalogo,
            @FormDataParam("servicio") String detallesServicio
    ){
        ServicioService servicio = new ServicioService();
        servicio.crearServicio(fotoServicio, catalogo, detallesServicio);
        return Response.ok()
                .build();
    }
    
    @Path("servicios_disponibles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerServiciosDisponibles(){
        ServicioService servicio = new ServicioService();
        return Response.ok(servicio.obtenerServiciosDisponibles())
                .build();
    }
    
    @Path("{idServicio}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerServicio(@PathParam("idServicio") String idServicio){
        ServicioService servicio = new ServicioService();
        return Response.ok(servicio.obtenerEntidad(idServicio))
                .build();
    }
    
    @Path("imagen_servicio/{idServicio}")
    @GET
    public Response obtenerImagenServicio(@PathParam("idServicio") String idServicio){
        ServicioService servicio = new ServicioService();
        Fotografia foto = servicio.encontrarFotoServicio(idServicio);
        return  Response.ok(foto.getFotografia())
                .header("Content-Type", foto.getExtension())
                .header("Content-Disposition", "inline; filename=\"imagen_servicio_"+ idServicio + "\"")
                .build();
    }
}
