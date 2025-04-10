/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.servicios.usuario;

import com.mycompany.salondebellezabe.Encriptador;
import com.mycompany.salondebellezabe.excepciones.ConeccionException;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NoAutorizadoException;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.modelos.enums.Rol;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import com.mycompany.salondebellezabe.servicios.Service;
import com.mycompany.salondebellezabe.validador.usuario.ValidadorUsuario;
import java.io.InputStream;
import java.util.Optional;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author rafael-cayax
 */
public class UsuarioService extends Service<Usuario>{
    private Usuario usuario;
    private final UsuarioDAO repositorioUsuario;
    private final ValidadorUsuario validadorUsuario;
    
    public UsuarioService(){
        super(new UsuarioDAO(), new ValidadorUsuario());
        this.repositorioUsuario = (UsuarioDAO) repositorio;
        this.validadorUsuario = (ValidadorUsuario) validador;
    }
    
    /**
     * metodo para obtener los datos del usuario en base a su contraseña y correo
     * @param usuario el correo y la contraseña del usuario
     * @return los datos que deba almacenar la aplicacion
     * @throws InvalidDataException en caso de no ingresar correctamente los datos
     * solicitados
     */
    public Usuario iniciarSesion(Usuario usuario){
        if (!validadorUsuario.esInicioDeSesionValido(usuario)) {
            throw new InvalidDataException("ingrese correctamente sus credenciales");
        }
        obtenerUsuario(usuario.getCorreo());
        comparaContraseñas(usuario);
        validarEstado();
        this.usuario.setContraseña(null);
        this.usuario.setCorreo(null);
        return this.usuario;
    }

    /**
     * metodo para obtener los datos del usuario 
     * @throws InvalidDataException en caso de que no se encuentre el usuario por
     * el correo
     * @throws ConeccionException en caso de que falle la coneccion a la base de datos
     */
    private void obtenerUsuario(String correo) {
            repositorioUsuario.setObtenerContraseña(true);
            Optional<Usuario> posibleUsuario = repositorioUsuario.buscarPorAtributo(correo);
            this.usuario = posibleUsuario.orElseThrow(() -> new InvalidDataException("correo o contraseña incorrectos"));
        
    }

    /**
     * metodo para comparar contraseñas y verificar si es la contraseña correcta
     * @param usuario la contraseña del usuario
     * @throws InvalidDataException en caso de no ser iguales
     */
    private void comparaContraseñas(Usuario usuario) {
        Encriptador encriptador = new Encriptador();
        if (!encriptador.esValida(usuario.getContraseña(), this.usuario.getContraseña())) {
            throw new InvalidDataException("correo o contraseña incorrectos");
        }
    }

    /**
     * metodo para validar el estado del usuario y si puede iniciar sesion
     * @throws NoAutorizadoException en caso de que el usuario no sea un cliente y
     * este desactivado
     */
    private void validarEstado() {
        if (!this.usuario.isActivo() && this.usuario.getRol() != Rol.CLIENTE) {
            throw new NoAutorizadoException("usuario desactivado, contacte con el administrador para reactivar");
        }
    }

    public void registrarUsuario(InputStream foto, FormDataContentDisposition detallesFoto, String detalles) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
