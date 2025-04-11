/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.excepciones.mappers;

import com.mycompany.salondebellezabe.excepciones.ConeccionException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author rafael-cayax
 */
@Provider
public class ConeccionExceptionMapper implements ExceptionMapper<ConeccionException>{

    @Override
    public Response toResponse(ConeccionException e) {
        String mensaje = "{\"mensaje\":\"no se pudo llevar a cabo la accion, intentar mas tarde\"}";
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(mensaje)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
