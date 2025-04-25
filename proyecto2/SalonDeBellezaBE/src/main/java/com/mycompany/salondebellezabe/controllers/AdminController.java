/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.consulta_reportes.Consulta;
import com.mycompany.salondebellezabe.reporte_cliente.ClienteReporteService;
import com.mycompany.salondebellezabe.reporte_servicio.ServicioReporteService;
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
    
    @Path("reporte_cliente_mas_citas")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clienteMasCitas(Consulta consulta){
        ClienteReporteService servicio = new ClienteReporteService();
        return Response.ok(servicio.obtenerUsuarioMasReservaciones(consulta))
                .build();
    }
    
    @Path("reporte_cliente_menos_citas")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response clienteMenosCitas(Consulta consulta){
        ClienteReporteService servicio = new ClienteReporteService();
        return Response.ok(servicio.obtenerUsuarioMenosResevacionse(consulta))
                .build();
    }
    
    @Path("reporte_servicios_ganancias")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerGananciasServicio(Consulta consulta){
        ServicioReporteService servicio = new ServicioReporteService();
        return Response.ok(servicio.obtenerGananciasServicio(consulta))
                .build();
    }
    
    @Path("reporte_ganancias_empleado")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerGananciasEmpleado(Consulta consulta){
        ServicioReporteService servicio = new ServicioReporteService();
        return Response.ok(servicio.obtenerGananciaEmpleado(consulta))
                .build();
    }
    
    @Path("reporte_citas_empleado")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEmpleadoMasCitas(Consulta consulta){
        ServicioReporteService servicio = new ServicioReporteService();
        return Response.ok(servicio.obtenerEmpleadosMasCitas(consulta))
                .build();
    }
}
