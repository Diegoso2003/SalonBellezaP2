/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service;

import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.validador.Validador;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;
import java.util.Optional;


/**
 *
 * @author rafael-cayax
 */
public abstract class Service<T> {
    protected ClaseDAO repositorio;
    protected Validador validador;
    private final String mensajeDeNoEncontrado;

    public Service(ClaseDAO repositorio, Validador validador, String mensaje) {
        this.repositorio = repositorio;
        this.validador = validador;
        this.mensajeDeNoEncontrado = mensaje;
    }
    
    public void crearEntidad(T entidad){
        validador.validarDatos(entidad);
        repositorio.insertar(entidad);
    }
    
    public void actualizar(T entidad){
        validador.validarActualizacion(entidad);
        repositorio.actualizar(entidad);
    }
    
    public T obtenerEntidad(String idEnviado){
        Integer id = this.validador.validarId(idEnviado);
        Optional<T> posibleEntidad = repositorio.obtenerPorID(id);
        return posibleEntidad.orElseThrow(() -> new NotFoundException(mensajeDeNoEncontrado));
    }

    public ClaseDAO getRepositorio() {
        return repositorio;
    }
    
}
