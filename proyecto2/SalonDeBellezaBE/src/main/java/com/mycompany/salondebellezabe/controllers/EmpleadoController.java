/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.dtos.CitasEmpleadoDiaDTO;
import com.mycompany.salondebellezabe.service.usuario.EmpleadoService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author rafael-cayax
 */
@Path("empleado")
public class EmpleadoController {
    
    @Path("agenda")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAgenda(CitasEmpleadoDiaDTO consulta){
        EmpleadoService servicio = new EmpleadoService();
        return Response.ok(servicio.obtenerCitasDelDia(consulta))
                .build();
    }
}
