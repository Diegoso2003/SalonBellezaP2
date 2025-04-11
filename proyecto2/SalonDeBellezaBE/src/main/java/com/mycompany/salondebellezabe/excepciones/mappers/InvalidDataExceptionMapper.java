/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.excepciones.mappers;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author rafael-cayax
 */
@Provider
public class InvalidDataExceptionMapper implements ExceptionMapper<InvalidDataException>{

    @Override
    public Response toResponse(InvalidDataException e) {
        String mensaje = "{\"mensaje\": \"" + e.getMessage() + "\"}";
        System.out.println("mapper aplicado correctamente");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(mensaje)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
