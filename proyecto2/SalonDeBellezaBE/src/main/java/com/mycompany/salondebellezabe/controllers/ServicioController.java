/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.service.servicio.ServicioService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response obtenerInformacionEmpleado(@PathParam("dpi") Long dpi){
        ServicioService servicio = new ServicioService();
        return Response.ok(servicio.obtenerInformacionEmpleado(dpi))
                .build();
    }
}
