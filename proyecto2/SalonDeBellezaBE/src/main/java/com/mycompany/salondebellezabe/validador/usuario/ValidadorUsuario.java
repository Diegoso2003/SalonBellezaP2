/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.validador.usuario;

import com.mycompany.salondebellezabe.Encriptador;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.modelos.enums.Rol;
import com.mycompany.salondebellezabe.validador.Validador;

/**
 *
 * @author rafael-cayax
 */
public class ValidadorUsuario extends Validador<Usuario>{
    
    private static final Long MENOR_DPI = 1_000_000_000_000L;
    private static final Long MAYOR_DPI = 9_999_999_999_999L;
    private static final Integer NOMBRE_MAS_CORTO = 6;
    private static final Integer NOMBRE_MAS_LARGO = 250;
    private static final Integer CORREO_MAS_CORTO = 6;
    private static final Integer CORREO_MAS_LARGO = 250;
    private static final String EXPRESION_REGULAR_CORREO_ACEPTADO = "^[\\w.-]+@[a-zA-ZñÑ]+(\\.[a-zA-ZñÑ]+)*$";
    private static final Integer CONTRASEÑA_MAS_CORTA = 6;
    private static final Integer CONTRASEÑA_MAS_LARGA = 60;
    private static final Integer TELEFONO_MAS_CORTO = 8;
    private static final Integer TELEFONO_MAS_LARGO = 20;
    private static final Integer DIRECCION_MAS_CORTA = 6;
    private static final Integer DIRECCION_MAS_LARGA = 250;
    private static final Integer GUSTOS_MAS_CORTO = 20;
    private static final Integer DESCRIPCION_MAS_CORTA = 20;
    private static final Integer HOBBIES_MAS_CORTO = 20;
    
    @Override
    protected boolean esValido() {
        Rol rol = this.entidad.getRol();
        if (rol == null) {
            return false;
        }
        switch(rol){
            case EMPLEADO:
                ValidadorFoto validadorFoto = new ValidadorFoto();
                validadorFoto.validarDatos(this.entidad.getFoto());
                return esEmpleadoValido();
            case CLIENTE:
                this.entidad.setActivo(false);
            case ADMINISTRADOR:
            case MARKETING:
            case SERVICIOS:
                return esUsuarioValido();
            default:
                return false;
        }
    }

    @Override
    public void validarDatos(Usuario usuario) {
        this.entidad = usuario;
        if (!esValido()) {
            throw new InvalidDataException("ingresar correctamente los datos solicitados");
        }
        Encriptador encriptador = new Encriptador();
        this.entidad.setContraseña(encriptador.encriptar(usuario.getContraseña()));
    }
    
    private boolean esUsuarioValido(){
        return esCorreoValido() && esContraseñaValida() && esConfirmacionContraseñaValida()
                && esNombreValido() && esTelefonoValido() && esDpiValido();
    }
    
    private boolean esEmpleadoValido(){
        return esUsuarioValido() && esDescripcionValida();
    }
    
    public boolean esInicioDeSesionValido(Usuario usuario){
        this.entidad = usuario;
        return usuario != null && esContraseñaValida() && esCorreoValido();
    }
    
    private boolean esContraseñaValida(){
        String contraseña = this.entidad.getContraseña();
        return cumpleRangoNormal(contraseña, CONTRASEÑA_MAS_CORTA, CONTRASEÑA_MAS_LARGA);
    }
    
    private boolean esConfirmacionContraseñaValida(){
        return this.entidad.getContraseña().equals(this.entidad.getConfirmacionContraseña());
    }
    
    private boolean esCorreoValido(){
        String correo = this.entidad.getCorreo();
        return cumpleRangoSinReemplazo(correo, CORREO_MAS_CORTO, CORREO_MAS_LARGO)
                && correo.matches(EXPRESION_REGULAR_CORREO_ACEPTADO);
    }
    
    private boolean esNombreValido(){
        String nombre = this.entidad.getNombre();
        return cumpleRangoConReemplazo(nombre, NOMBRE_MAS_CORTO, NOMBRE_MAS_LARGO);
    }
    
    private boolean esDpiValido(){
        Long dpi = this.entidad.getDpi();
        return dpi != null && dpi >= MENOR_DPI
                && dpi <= MAYOR_DPI;
    }
    
    private boolean esTelefonoValido(){
        String telefono = this.entidad.getTelefono();
        return cumpleRangoConReemplazo(telefono, TELEFONO_MAS_CORTO, TELEFONO_MAS_LARGO);
    }
    
    private boolean esDireccionValida(){
        String direccion = this.entidad.getDireccion();
        return cumpleRangoConReemplazo(direccion, DIRECCION_MAS_CORTA, DIRECCION_MAS_LARGA);
    }
    
    private boolean sonHobbiesValidos(){
        String hobbies = this.entidad.getHobbies();
        return esLargoMinimo(hobbies, HOBBIES_MAS_CORTO);
    }
    
    private boolean sonGustosValidos(){
        String gustos = this.entidad.getGustos();
        return esLargoMinimo(gustos, GUSTOS_MAS_CORTO);
    }
    
    private boolean esDescripcionValida(){
        String descripcion = this.entidad.getDescripcion();
        return esLargoMinimo(descripcion, DESCRIPCION_MAS_CORTA);
    }

    public void validarDetalles(Usuario usuario) {
        this.entidad = usuario;
        if (!sonDetallesValidos()) {
            throw new InvalidDataException("ingresar correctamente los datos solicitados");
        }
        ValidadorFoto validador = new ValidadorFoto();
        validador.validarDatos(usuario.getFoto());
    }
    
    public boolean sonDetallesValidos(){
        return esDescripcionValida() && sonHobbiesValidos() && sonGustosValidos() 
                && esDireccionValida() && esDpiValido();
    }
    
}
