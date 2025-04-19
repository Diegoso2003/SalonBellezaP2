/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.dtos.CitasEmpleadoDiaDTO;
import com.mycompany.salondebellezabe.dtos.HorarioEmpleadoDTO;
import com.mycompany.salondebellezabe.service.usuario.ClienteService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
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
    
    @Path("obtener_horario_empleado/{dpi}/{fecha}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerHorarioEmpleado(
            @PathParam("dpi") String dpi,
            @PathParam("fecha") String fecha
    ){
        ClienteService servicio = new ClienteService();
        List<HorarioEmpleadoDTO> horario = servicio.obtenerHorario(dpi, fecha);
        return Response.ok(horario)
                .build();
    }
}
