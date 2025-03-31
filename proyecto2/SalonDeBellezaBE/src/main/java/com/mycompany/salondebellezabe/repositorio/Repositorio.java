/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 * @param <T> la clase de la entidad
 * @param <ID> la clase que use como id
 */
public abstract class Repositorio<T, ID> {
    protected Connection coneccion;

    public Repositorio(Connection coneccion) {
        this.coneccion = coneccion;
    }
    
    public abstract Integer insertar(T entidad);
    public abstract void eliminar(ID id);
    public abstract Optional<T> obtenerPorID(ID id);
    public abstract void actualizar(T entidad);
    public abstract List<T> obtenerTodo();
    protected abstract T obtenerDatos(ResultSet result) throws SQLException;
}
