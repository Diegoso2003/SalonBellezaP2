/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.ArchivosServicio;
import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.repositorio.servicios.ServicioDAO;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import com.mycompany.salondebellezabe.service.Service;
import com.mycompany.salondebellezabe.validador.servicio.ValidadorServicio;
import java.util.List;
import java.util.Optional;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

/**
 *
 * @author rafael-cayax
 */
public class ServicioService extends Service<Servicio>{
    private final ServicioDAO repositorioServicio;
    private final ValidadorServicio validadorServicio;
    private Servicio servicio;
    
    public ServicioService() {
        super(new ServicioDAO(), new ValidadorServicio());
        this.repositorioServicio = (ServicioDAO)repositorio;
        this.validadorServicio = (ValidadorServicio) validador;
    }
    
    /**
     * metodo usado para obtener los empleados que esten activos
     * @return los empleados activos
     */
    public List<Usuario> obtenerEmpleados(){
        UsuarioDAO repositorioUsuario = new UsuarioDAO();
        return repositorioUsuario.obtenerEmpleados();
    }
    
    /**
     * metodo usado para obtener la descripcion de un empleado
     * @param dpi el dpi del empleado
     * @return el usuario con su descripcion y detalles
     */
    public Usuario obtenerInformacionEmpleado(Long dpi){
        UsuarioDAO repositorioUsuario = new UsuarioDAO();
        repositorioUsuario.setObtenerDatos(true);
        Optional<Usuario> posibleUsuario = repositorioUsuario.obtenerPorID(dpi);
        return posibleUsuario.orElseThrow(() -> new NotFoundException("empleado no encontrado"));
    }

    /**
     * metodo para crear un servicio
     * @param fotoServicio la foto del servicio
     * @param catalogo el catalogo del servicio
     * @param detallesServicio los datos del servicio en formato json
     */
    public void crearServicio(FormDataBodyPart fotoServicio, FormDataBodyPart catalogo, String detallesServicio) {
        servicio = obtenerDatosServicio(detallesServicio);
        ArchivosServicio archivos = new ArchivosServicio();
        obtenerImagen(fotoServicio, archivos);
        obtenerCatalogo(catalogo, archivos);
        servicio.setArchivos(archivos);
        this.crearEntidad(servicio);
    }

    /**
     * obtiene la imagen del servicio
     * @param fotoServicio la foto del servicio
     * @param archivos los archivos del servicio
     */
    private void obtenerImagen(FormDataBodyPart fotoServicio, ArchivosServicio archivos) {
        if (fotoServicio == null) {
            throw new InvalidDataException("enviar la foto del servicio");
        }
        Fotografia foto = new Fotografia();
        foto.setFoto(fotoServicio.getContent());
        foto.setExtension(fotoServicio.getMediaType().toString());
        archivos.setFoto(foto);
    }

    /**
     * metodo para obtener el catalogo del servicio
     * @param catalogo el catalogo del servicio
     * @param archivos los archivos del servicio
     */
    private void obtenerCatalogo(FormDataBodyPart catalogo, ArchivosServicio archivos) {
        if (catalogo == null) {
            throw new InvalidDataException("enviar el catalogo del servicio");
        }
        archivos.setCatalogo(catalogo.getContent());
        archivos.setExtensionCatalogo(catalogo.getMediaType().toString());
    }

    /**
     * metodo para obtener los datos del servicio
     * @param detallesServicio los datos en formato json
     * @return 
     */
    private Servicio obtenerDatosServicio(String detallesServicio) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(detallesServicio, Servicio.class);
        } catch (Exception e) {
            System.out.println("error atrapado");
            System.out.println(e);
            throw new InvalidDataException("ingrese correctamente los datos solicitados");
        }
    }
    
}
