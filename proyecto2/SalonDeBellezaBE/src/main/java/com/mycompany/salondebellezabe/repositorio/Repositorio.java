/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio;

import com.mycompany.salondebellezabe.Coneccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 * @param <T> la clase de la entidad
 * @param <ID> el tipo de dato que use como id
 */
public abstract class Repositorio<T, ID> {
    protected Integer idGenerado;
    
    public abstract void insertar(T entidad);
    public abstract void eliminar(ID id);
    public abstract Optional<T> obtenerPorID(ID id);
    public abstract void actualizar(T entidad);
    public abstract List<T> obtenerTodo();
    protected abstract T obtenerDatos(ResultSet result) throws SQLException;
    
    /**
     * metodo usado para listar las entidades en base a las restricciones de la
     * consulta
     * @param consulta consulta con las restricciones
     * @return una lista con las entidades que encajen con lo pedido
     */
    protected List<T> listarPorAtributos(String consulta){
        List<T> entidades = new ArrayList<>();
        try (Connection coneccion = Coneccion.getConeccion();
                Statement stmt = coneccion.createStatement();
                ResultSet result = stmt.executeQuery(consulta)){
            while(result.next()){
                T entidad = obtenerDatos(result);
                entidades.add(entidad);
            }
        } catch (SQLException e) {
            //algo salio mal
        }
        return entidades;
    }
    
    /**
     * metodo usado para buscar una entidad dentro de la tabla por medio de una
     * columna que sea unique o llave primaria
     * @param query la consulta
     * @param columna el valor de la columna
     * @param tipo tipo de dato como JDBCType.Integer, JSDBCType.VARCHAR, etc
     * @return un optional con la entidad si es encontrada
     */
    protected Optional<T> buscar(String query, Object columna, SQLType tipo){
        try (Connection coneccion = Coneccion.getConeccion();
                PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setObject(1, columna, tipo);
            try(ResultSet result = stmt.executeQuery()){
                if (result.next()) {
                    T entidad = obtenerDatos(result);
                    return Optional.of(entidad);
                }
            }
        } catch (SQLException e) {
            //informar sobre un error con el parametro enviado
        }
        return Optional.empty();
    }

    /**
     * metodo para obtener el id generado al insertar un elemento en una tabla 
     * con una columna incrementable
     * @return el id generado si es usado para insertar en una tabla con auto incrementable
     * de lo contrario devuelve null
     */
    public Integer getIdGenerado() {
        return idGenerado;
    }
    
}
