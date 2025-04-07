/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.excepciones.mappers;

import com.mycompany.salondebellezabe.excepciones.NoAutorizadoException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author rafael-cayax
 */
@Provider
public class NoAutorizadoExcetionMapper implements ExceptionMapper<NoAutorizadoException>{

    @Override
    public Response toResponse(NoAutorizadoException e) {
        String mensaje = "{\"mensaje\":\"" + e.getMessage() +  "\"}";
        return Response.status(Response.Status.FORBIDDEN)
                .entity(mensaje)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
