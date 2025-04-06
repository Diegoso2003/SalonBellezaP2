/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.servicios;

import com.mycompany.salondebellezabe.Coneccion;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public abstract class Servicio<T> {
    private Repositorio repositorio;
    private T entidad;

    public void crearEntidad(T entidad){
        this.entidad = entidad;
        validarDatos();
    }
    
    protected abstract void validarDatos();
    
    public void actualizar(T entidad){
        this.entidad = entidad;
        validarDatos();
        repositorio.actualizar(entidad);
    }

}
