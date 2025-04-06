/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rafael-cayax
 */
public class Coneccion {
    
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/laComputadoraFeliz";
    private static final String USER = "root";
    private static final String PASSWORD = "Programacion";
    private static Connection coneccion;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC", e);
        }
    }
    
    private Coneccion() {}
    
    public static Connection getConeccion() throws SQLException{
        if (coneccion == null || coneccion.isClosed()) {
        synchronized (Coneccion.class) {
            if (coneccion == null || coneccion.isClosed()) {
                coneccion = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            }
        }
    }
    return coneccion;
    }

}
