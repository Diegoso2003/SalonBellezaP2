/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.controllers;

import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.dtos.LoginDTO;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.service.usuario.UsuarioService;
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
@Path("usuario")
public class UsuarioController {
    
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarSesion(Usuario usuario){
        UsuarioService servicio = new UsuarioService();
        LoginDTO login = servicio.iniciarSesion(usuario);
        return Response.ok(login)
                .build();
    }
    
    @Path("registro_usuario")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarCliente(Usuario usuario){
        UsuarioService servicio = new UsuarioService();
        servicio.crearEntidad(usuario);
        return Response.ok()
                .build();
    }
    
    @Path("registro_empleado")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response registrarUsuario(
            @FormDataParam("foto") FormDataBodyPart foto,
            @FormDataParam("detalles") String detalles
    ){
        UsuarioService servicio = new UsuarioService();
        servicio.registrarUsuario(foto, detalles);
        return Response.ok()
                .build();
    }
    
    @Path("/imagen_perfil/{dpi}")
    @GET
    public Response conseguirFotoPerfil(@PathParam("dpi") String dpi){
        UsuarioService servicio = new UsuarioService();
        Fotografia foto = servicio.obtenerFotoPerfil(dpi);
        return Response.ok(foto.getFotografia())
                .header("Content-Type", foto.getExtension())
                .header("Content-Disposition", "inline; filename=\"foto_perfil_"+ dpi + "\"")
                .build();
    }
}
