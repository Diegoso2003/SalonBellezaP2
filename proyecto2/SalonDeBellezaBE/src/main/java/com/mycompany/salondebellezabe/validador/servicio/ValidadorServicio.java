/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.validador.servicio;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.validador.Validador;
import com.mycompany.salondebellezabe.validador.usuario.ValidadorFoto;
import java.time.LocalTime;
import java.util.Set;

/**
 *
 * @author rafael-cayax
 */
public class ValidadorServicio extends Validador<Servicio>{
    private static final Integer NOMBRE_MAS_LARGO = 200;
    private static final Integer NOMBRE_MAS_CORTO = 6;
    private static final Integer DESCRIPCION_CORTA = 20;
    private static final LocalTime DURACION_MAS_LARGA = LocalTime.of(4, 59);
    private static final LocalTime DURACION_MAS_CORTA = LocalTime.of(0, 10);
    private static final String PDF = "application/pdf";

    @Override
    protected boolean esValido() {
        return esNombreValido() && esDescripcionValida() && esPrecioValido() && esDuracionValida();
    }

    @Override
    public void validarDatos(Servicio servicio) {
        this.entidad = servicio;
        validarEmpleados();
        if (!esValido()) {
            throw new InvalidDataException("ingresar correctamente los datos solicitados");
        }
        ValidadorFoto validadorFoto = new ValidadorFoto();
        validadorFoto.validarDatos(entidad.getArchivos().getFoto());
        if (!entidad.getArchivos().getExtensionCatalogo().equals(PDF)) {
            throw new InvalidDataException("ingresar un catalogo valido");
        }
    }

    private void validarEmpleados() {
        Set<Usuario> empleados = entidad.getEmpleados();
        if (empleados == null || empleados.isEmpty()) {
            throw new InvalidDataException("para crear el servicio es necesario incluir al menos un empleado");
        }
    }
    
    private boolean esNombreValido(){
        String nombre = entidad.getNombreServicio();
        return cumpleRangoConReemplazo(nombre, NOMBRE_MAS_CORTO, NOMBRE_MAS_LARGO);
    }
    
    private boolean esDescripcionValida(){
        String descripcion = entidad.getDescripcion();
        return esLargoMinimo(descripcion, DESCRIPCION_CORTA);
    }
    
    private boolean esDuracionValida() {
        LocalTime duracion = entidad.getDuracion();
        if (duracion == null) {
            return false;
        }
        System.out.println("duracion: " + duracion);
        return duracion.isAfter(DURACION_MAS_CORTA) && duracion.isBefore(DURACION_MAS_LARGA);
    }
    
    private boolean esPrecioValido(){
        double precio = entidad.getPrecio();
        return precio > 0;
    }

    @Override
    public void validarActualizacion(Servicio entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
