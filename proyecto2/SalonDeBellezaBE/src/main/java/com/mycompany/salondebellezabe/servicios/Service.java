/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.servicios;

import com.mycompany.salondebellezabe.Coneccion;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author rafael-cayax
 */
public abstract class Service<T> {
    protected Repositorio repositorio;

    public Service(Repositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    public void crearEntidad(T entidad){
        validarDatos(entidad);
        try (Connection coneccion = Coneccion.getConeccion()){
            repositorio.setConeccion(coneccion);
            repositorio.insertar(entidad);
        } catch (SQLException e) {
            //error al conectarse a la base de datos
        }
    }
    
    protected abstract void validarDatos(T entidad);
    
    public void actualizar(T entidad){
        validarDatos(entidad);
        try (Connection coneccion = Coneccion.getConeccion()){
            repositorio.setConeccion(coneccion);
            repositorio.actualizar(entidad);
        } catch (SQLException e) {
        
        }
    }

    public Repositorio getRepositorio() {
        return repositorio;
    }
    
}
