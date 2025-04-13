/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service;

import com.mycompany.salondebellezabe.validador.Validador;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;


/**
 *
 * @author rafael-cayax
 */
public abstract class Service<T> {
    protected ClaseDAO repositorio;
    protected Validador validador;

    public Service(ClaseDAO repositorio, Validador validador) {
        this.repositorio = repositorio;
        this.validador = validador;
    }
    
    public void crearEntidad(T entidad){
        validador.validarDatos(entidad);
        repositorio.insertar(entidad);
    }
    
    public void actualizar(T entidad){
        validador.validarDatos(entidad);
        repositorio.actualizar(entidad);
    }

    public ClaseDAO getRepositorio() {
        return repositorio;
    }
    
}
