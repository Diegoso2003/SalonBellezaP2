/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.servicios;

import com.mycompany.salondebellezabe.repositorio.ClaseDAO;


/**
 *
 * @author rafael-cayax
 */
public abstract class Service<T> {
    protected ClaseDAO repositorio;

    public Service(ClaseDAO repositorio) {
        this.repositorio = repositorio;
    }
    
    public void crearEntidad(T entidad){
        validarDatos(entidad);
        repositorio.insertar(entidad);
    }
    
    protected abstract void validarDatos(T entidad);
    
    public void actualizar(T entidad){
        validarDatos(entidad);
        repositorio.actualizar(entidad);
    }

    public ClaseDAO getRepositorio() {
        return repositorio;
    }
    
}
