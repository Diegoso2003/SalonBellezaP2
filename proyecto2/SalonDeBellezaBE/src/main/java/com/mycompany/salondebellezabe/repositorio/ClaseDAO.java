/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * clase diseñada para seguir el patron dao para bases de datos usando mysql
 * @implSpec todos los metodos que usen una coneccion deben de usarl el metodo
 * de obtener coneccion {@link #obtenerConeccion()} ya que otras clases repositorio
 * podrian hacer procesos por lo cual compartiran la misma coneccion usando 
 * {@link #setConeccion(java.sql.Connection) } para asegurar usar la misma coneccion
 * ademas de usar internamente {@link #cerrar() } para cerrar la coneccion al final
 * @author rafael-cayax
 * @param <T> la clase de la entidad
 * @param <ID> el tipo de dato que use como id
 */
public abstract class ClaseDAO<T, ID> extends Repositorio{
    protected Integer idGenerado;
    
    /**
     * metodo usado para insertar una entidad en la base de datos
     * @implSpec este metodo debe ser implementado para poder insertar una fila
     * a la tabla de una base de datos, de trabajar con ids auto increment usar 
     * {@link #idGenerado} para almacenar el id generado
     * @apiNote debe de usar {@link #obtenerConeccion() } para obtener una coneccion
     * y en el finally usar {@link #cerrar() } para cerrar la coneccion
     * @param entidad la entidad a guardar
     */
    public abstract void insertar(T entidad);
    
    /**
     * metodo usado para eliminar o desactivar una entidad en la base de datos
     * @implSpec este metodo debe de ser implementado para eliminar un dato de la 
     * base de datos o solo cambiar su estado
     * @apiNote debe de usar {@link #obtenerConeccion() } para obtener una coneccion
     * y en el finally usar {@link #cerrar() } para cerrar la coneccion
     * @param id el id de la entidad
     */
    public abstract void eliminar(ID id);
    
    /**
     * metodo para obtener una entidad de la base de datos por su primary key
     * @implSpec este metodo debe de ser implementado para obtener un posible dato
     * que exista en la base de datos en caso de no existir el optional debe de regresar
     * vacio
     * @apiNote si se va a usar {@link #buscar(java.lang.String, java.lang.Object, java.sql.SQLType) }
     * no es necesario aplicar lo siguiente:
     * debe de usar {@link #obtenerConeccion() } para obtener una coneccion
     * y en el finally usar {@link #cerrar() } para cerrar la coneccion
     * @param id el id de la entidad
     * @return un optional con la entidad
     */
    public abstract Optional<T> obtenerPorID(ID id);
    
    /**
     * metodo para actualizar un dato de la base de datos
     * @implSpec este metodo debe de ser implementado para actualizar una fila en 
     * alguna tabla de la base de datos, debe de ingresar la entidad con los datos
     * a actualizar
     * @apiNote debe de usar {@link #obtenerConeccion() } para obtener una coneccion
     * y en el finally usar {@link #cerrar() } para cerrar la coneccion
     * @param entidad los datos de la entidad
     */
    public abstract void actualizar(T entidad);
    
    /**
     * @implSpec para consultas simples solo es necesario armar la consulta
     * y usar {@link #listarPorAtributos(java.lang.String) }
     * @apiNote si se va a usar {@link #listarPorAtributos(java.lang.String) }
     * no es necesario aplicar lo siguiente
     * debe de usar {@link #obtenerConeccion() } para obtener una coneccion
     * y en el finally usar {@link #cerrar() } para cerrar la coneccion
     * @return 
     */
    public abstract List<T> obtenerTodo();
    
    /**
     * metodo usado para obtener los resultados de una consulta
     * @apiNote metodo que debe ser implementado para obtener los datos de la entida
     * en base a una consulta
     * @param result los datos de la consulta
     * @return el objeto entidad
     * @throws SQLException 
     */
    protected abstract T obtenerDatos(ResultSet result) throws SQLException;
    
    /**
     * metodo usado para listar las entidades en base a las restricciones de la
     * consulta
     * @param consulta consulta con las restricciones
     * @return una lista con las entidades que encajen con lo pedido
     */
    protected List<T> listarPorAtributos(String consulta){
        List<T> entidades = new ArrayList<>();
        obtenerConeccion();
        try (Statement stmt = coneccion.createStatement();
                ResultSet result = stmt.executeQuery(consulta)){
            while(result.next()){
                T entidad = obtenerDatos(result);
                entidades.add(entidad);
            }
        } catch (SQLException e) {
            //algo salio mal
        } finally {
            cerrar();
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
        obtenerConeccion();
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setObject(1, columna, tipo);
            try(ResultSet result = stmt.executeQuery()){
                if (result.next()) {
                    T entidad = obtenerDatos(result);
                    return Optional.of(entidad);
                }
            }
        } catch (SQLException e) {
            //informar sobre un error con el parametro enviado
        } finally {
            cerrar();
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

    protected void regresar(){
        try {
            coneccion.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
