/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.service.usuario.ClienteService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author rafael-cayax
 */
@Path("cliente")
public class ClienteController {
    
    @Path("detalles")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response añadirDetalles(
            @FormDataParam("foto") FormDataBodyPart cuerpo,
            @FormDataParam("datos") String detalles
    ){
        ClienteService servicio = new ClienteService();
        servicio.agregarDetalles(cuerpo, detalles);
        return Response.ok()
                .build();
    }
}
