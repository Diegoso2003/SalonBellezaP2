/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.anuncios;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.Fotografia;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;
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
public class ImagenAnuncioDAO extends ClaseDAO<Fotografia, Integer>{

    private Integer idAnuncio;

    public ImagenAnuncioDAO(Integer idAnuncio) {
        this.idAnuncio = idAnuncio;
    }
    
    public ImagenAnuncioDAO(){}
    /**
     * metodo para insertar la imagen de un anuncio a la base de datos
     * @param imagen los datos de la imagen del anuncio
     */
    @Override
    public void insertar(Fotografia imagen) {
        obtenerConeccion();
        String query = "INSERT INTO ImagenAnuncio(idAnuncio, imagen, extension)"
                + " VALUES(?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, idAnuncio);
            stmt.setBlob(2, imagen.getFoto());
            stmt.setString(3, imagen.getExtension());
            stmt.executeUpdate();
        } catch (SQLException e) {
            regresar();
            if (e.getErrorCode() == 1406) {
                throw new InvalidDataException("imagen ingresada muy pesada ingrese otra que pese menos de 64kb");
            }
            throw new InvalidDataException("imagen ingresada no valida, ingrese otra");
        } finally {
            cerrar();
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
    public Optional<Fotografia> obtenerPorID(Integer id) {
        String query = "SELECT * FROM Fotografia WHERE idAnuncio = ?";
        return buscar(query, id, JDBCType.INTEGER);
    }

    /**
     * metodo para actualizar la imagen de un anuncio
     * @param imagen los datos de la imagen
     */
    @Override
    public void actualizar(Fotografia imagen) {
        obtenerConeccion();
        String query = "UPDATE Fotografia SET extension = ?, imagen = ? WHERE idAnuncio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setString(1, imagen.getExtension());
            stmt.setBlob(2, imagen.getFoto());
            stmt.setInt(3, idAnuncio);
            if (stmt.executeUpdate() <= 0) {
                //anuncio no encontrado
            }
        } catch (SQLException e) {
            //imagen ingresada es invalida
        } finally {
            cerrar();
        }
    }

    @Override
    public List<Fotografia> obtenerTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo para poder obtener los datos de la imagen de una consulta
     * @param result el resultado de la consulta
     * @return la imagen con los datos
     * @throws SQLException 
     */
    @Override
    protected Fotografia obtenerDatos(ResultSet result) throws SQLException {
        Fotografia imagen = new Fotografia();
        imagen.setExtension(result.getString("extension"));
        imagen.setFotografia(result.getBytes("imagen"));
        return imagen;
    }
    
}
