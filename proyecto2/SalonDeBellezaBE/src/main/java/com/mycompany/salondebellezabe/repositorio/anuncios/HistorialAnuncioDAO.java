/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.anuncios;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.HistorialAnuncio;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class HistorialAnuncioDAO extends ClaseDAO<HistorialAnuncio, HistorialAnuncio>{

    /**
     * metodo para insertar la fecha y el url donde se mostro el anuncio, en caso 
     * de ya existir en la base de datos el contador aumenta en uno por cada visita
     * @param historial los datos del historial del anuncio
     */
    @Override
    public void insertar(HistorialAnuncio historial) {
        obtenerConeccion();
        String query = "INSERT INTO HistorialAnuncio(url, fechaPublicacion, idAnuncio)"
                + " VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE contador = contador + 1";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setString(1, historial.getUrl());
            stmt.setDate(2, Date.valueOf(historial.getFechaPublicacion()));
            stmt.setInt(3, historial.getIdAnuncio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidDataException("datos enviados para el historial del anuncio invalidos");
        } finally {
            cerrar();
        }
    }

    @Override
    public void eliminar(HistorialAnuncio id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<HistorialAnuncio> obtenerPorID(HistorialAnuncio id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar(HistorialAnuncio entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HistorialAnuncio> obtenerTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected HistorialAnuncio obtenerDatos(ResultSet result) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
