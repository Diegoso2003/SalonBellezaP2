/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio;

import com.mycompany.salondebellezabe.PoolConnections;
import com.mycompany.salondebellezabe.consulta_reportes.Consulta;
import com.mycompany.salondebellezabe.excepciones.ConeccionException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author rafael-cayax
 */
public class Repositorio {
    protected Connection coneccion;    
    private boolean compartirConeccion = false;
    protected Integer indice = 1;
    /**
     * metodo usado para obtener la coneccion a la base de datos si no ha sido 
     * setteada de lo contrario no la consigue
     * @apiNote si se va a usar este metodo no olvidar poner en el finally el metodo
     * {@link #cerrar() }
     * @throws ConeccionException si al conseguir la coneccion falla algo
     */
    protected void obtenerConeccion(){
        if (!compartirConeccion || coneccion == null) {
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
    
    protected void colocarFechas(Consulta consulta, PreparedStatement stmt) throws SQLException{
        if (consulta.tieneAmbas()) {
            System.out.println("ambas");
            stmt.setDate(indice, Date.valueOf(consulta.getFechaInicio()));
            indice++;
            stmt.setDate(indice, Date.valueOf(consulta.getFechaFin()));
        } else if(consulta.tieneFechaInicio()){
            System.out.println("inicio");
            stmt.setDate(indice, Date.valueOf(consulta.getFechaInicio()));
        } else if(consulta.tieneFechaFin()){
            System.out.println("fin");
            stmt.setDate(indice, Date.valueOf(consulta.getFechaFin()));
        }
    }
}
