/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.anuncios;

import com.mycompany.salondebellezabe.Coneccion;
import com.mycompany.salondebellezabe.modelos.ImagenAnuncio;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class ImagenAnuncioDAO extends Repositorio<ImagenAnuncio, Integer>{


    /**
     * metodo para insertar la imagen de un anuncio a la base de datos
     * @param imagen los datos de la imagen del anuncio
     */
    @Override
    public void insertar(ImagenAnuncio imagen) {
        String query = "INSERT INTO ImagenAnuncio(idAnuncio, imagen, extension)"
                + " VALUES(?, ?, ?)";
        try (Connection coneccion = Coneccion.getConeccion();
                PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, imagen.getIdAnuncio());
            stmt.setBlob(2, imagen.getImagen());
            stmt.setString(3, imagen.getExtension());
            stmt.executeUpdate();
        } catch (SQLException e) {
            //imagen ingresada invalida
        }
    }

    @Override
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo para obtener la imagen de un anuncio por su id
     * @param id el id del anuncio
     * @return un optional con los datos de la imagen
     */
    @Override
    public Optional<ImagenAnuncio> obtenerPorID(Integer id) {
        String query = "SELECT * FROM ImagenAnuncio WHERE idAnuncio = ?";
        return buscar(query, id, JDBCType.INTEGER);
    }

    /**
     * metodo para actualizar la imagen de un anuncio
     * @param imagen los datos de la imagen
     */
    @Override
    public void actualizar(ImagenAnuncio imagen) {
        String query = "UPDATE ImagenAnuncio SET extension = ?, imagen = ? WHERE idAnuncio = ?";
        try (Connection coneccion = Coneccion.getConeccion();
                PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setString(1, imagen.getExtension());
            stmt.setBlob(2, imagen.getImagen());
            stmt.setInt(3, imagen.getIdAnuncio());
            if (stmt.executeUpdate() <= 0) {
                //anuncio no encontrado
            }
        } catch (SQLException e) {
            //imagen ingresada es invalida
        }
    }

    @Override
    public List<ImagenAnuncio> obtenerTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo para poder obtener los datos de la imagen de una consulta
     * @param result el resultado de la consulta
     * @return la imagen con los datos
     * @throws SQLException 
     */
    @Override
    protected ImagenAnuncio obtenerDatos(ResultSet result) throws SQLException {
        ImagenAnuncio imagen = new ImagenAnuncio();
        imagen.setExtension(result.getString("extension"));
        imagen.setFoto(result.getBytes("imagen"));
        imagen.setIdAnuncio(result.getInt("idAnuncio"));
        return imagen;
    }
    
}
