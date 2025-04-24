/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.consulta_reportes.Consulta;
import com.mycompany.salondebellezabe.reporte_cliente.ClienteReporteService;
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
@Path("admin")
public class AdminController {
    
    @Path("reporte_cliente_mayor_gasto")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clienteMasGasto(Consulta consulta){
        ClienteReporteService servicio = new ClienteReporteService();
        return Response.ok(servicio.obtenerUsuarioMasGasta(consulta))
                .build();
    }
    
    @Path("reporte_cliente_menor_gasto")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clienteMenosGasto(Consulta consulta){
        ClienteReporteService servicio = new ClienteReporteService();
        return Response.ok(servicio.obtenerUsuarioMenosGasto(consulta))
                .build();
    }
}
