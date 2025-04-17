/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio;

import com.mycompany.salondebellezabe.PoolConnections;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rafael-cayax
 * @param <T>
 */
public abstract class TablasUnicas<T> extends Repositorio{
    
    private final String nombreTabla;

    public TablasUnicas(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public T obtenerDatos(){
        T entidad = null;
        obtenerConeccion();
        String query = "SELECT * FROM " + nombreTabla;
        try (Statement stmt = coneccion.createStatement();
                ResultSet result = stmt.executeQuery(query)){
            if (result.next()) {
                entidad = obtenerDatos(result);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            cerrar();
        }
        return entidad;
    }
    
    public abstract void actualizarTabla(T entidad);
    protected abstract T obtenerDatos(ResultSet resultadoConsulta) throws SQLException;
}
