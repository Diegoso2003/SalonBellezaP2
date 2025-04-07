/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.servicios.usuario.UsuarioService;
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
@Path("usuario")
public class UsuarioController {
    
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarSesion(Usuario usuario){
        UsuarioService servicio = new UsuarioService();
        Usuario usuario2 = servicio.iniciarSesion(usuario);
        return Response.ok(usuario2)
                .build();
    }
}
