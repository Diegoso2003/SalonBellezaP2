/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.usuario;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import com.mycompany.salondebellezabe.service.Service;
import com.mycompany.salondebellezabe.validador.usuario.ValidadorUsuario;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

/**
 *
 * @author rafael-cayax
 */
public class ClienteService extends Service<Usuario>{
    private final ValidadorUsuario validadorUsuario;
    private final UsuarioDAO usuarioDAO;
    
    public ClienteService() {
        super(new UsuarioDAO(), new ValidadorUsuario(), "cliente no encontrado");
        validadorUsuario = (ValidadorUsuario) validador;
        usuarioDAO = (UsuarioDAO) repositorio;
    }

    public void agregarDetalles(FormDataBodyPart foto, String detalles) {
        try {
            Fotografia fotoUsuario = new Fotografia();
            if (foto == null) {
                throw new InvalidDataException("ingresar correctamente la foto del usuario");
            }
            fotoUsuario.setFoto(foto.getContent());
            fotoUsuario.setExtension(foto.getMediaType().toString());
            ObjectMapper mapper = new ObjectMapper();
            Usuario usuario = mapper.readValue(detalles, Usuario.class);
            usuario.setFoto(fotoUsuario);
            validadorUsuario.validarDetalles(usuario);
            usuarioDAO.ingresarDetalles(usuario);
        } catch (JsonProcessingException ex) {
            throw new InvalidDataException("ingrese correctamente los datos solicitados");
        }
    }
    
    
}
