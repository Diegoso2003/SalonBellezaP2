/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio;

import com.mycompany.salondebellezabe.PoolConnections;
import com.mycompany.salondebellezabe.excepciones.ConeccionException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author rafael-cayax
 */
public class Repositorio {
    protected Connection coneccion;    
    private boolean compartirConeccion = false;
    /**
     * metodo usado para obtener la coneccion a la base de datos si no ha sido 
     * setteada de lo contrario no la consigue
     * @throws ConeccionException si al conseguir la coneccion falla algo
     */
    protected void obtenerConeccion(){
        if (!compartirConeccion) {
            try {
                this.coneccion = PoolConnections.getInstance().getDatasource().getConnection();
            } catch (SQLException ex) {
                throw new ConeccionException("Error intentar mas tarde");
            }
        }
    }
    
    /**
     * metodo para cerrar la conexion si no ha sido cerrada
     */
    protected void cerrar(){
        if (!compartirConeccion) {
            try {
                this.coneccion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * al usar este metodo se reinicia el valor de la variable {@link #compartirConeccion}
     * a su valor por defecto
     */
    public void reiniciarEstado(){
        this.compartirConeccion = false;
    }

    /**
     * metodo usado por otras clases que hereden de {@link #Repositorio() }
     * para compartir la misma coneccion
     * @param coneccion
     */
    public void setConeccion(Connection coneccion) {
        this.compartirConeccion = coneccion != null;
        this.coneccion = coneccion;
    }
}
