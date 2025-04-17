/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.salon_belleza;

import com.mycompany.salondebellezabe.modelos.PreciosAnuncio;
import com.mycompany.salondebellezabe.repositorio.TablasUnicas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rafael-cayax
 */
public class PreciosAnuncioRep extends TablasUnicas<PreciosAnuncio>{

    public PreciosAnuncioRep() {
        super("PreciosAnuncio");
    }

    /**
     * metodo usado para actualizar los precios de los anuncios
     * @param entidad los precios nuevos
     */
    @Override
    public void actualizarTabla(PreciosAnuncio entidad) {
        String query = "UPDATE PreciosAnuncio SET texto = ?, video = ?, imagen = ?";
        obtenerConeccion();
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setDouble(1, entidad.getTexto());
            stmt.setDouble(2, entidad.getVideo());
            stmt.setDouble(3, entidad.getImagen());
            stmt.executeUpdate();
        } catch (SQLException e) {
            //valores ingresados invalidos
        } finally {
            cerrar();
        }
    }

    /**
     * metodo para obtener los precios de los anuncios por dia
     * @param resultadoConsulta el resultado de la consulta
     * @return los precios actuales
     * @throws SQLException 
     */
    @Override
    protected PreciosAnuncio obtenerDatos(ResultSet resultadoConsulta) throws SQLException {
        PreciosAnuncio precios = new PreciosAnuncio();
        precios.setTexto(resultadoConsulta.getDouble("texto"));
        precios.setImagen(resultadoConsulta.getDouble("imagen"));
        precios.setVideo(resultadoConsulta.getDouble("video"));
        return precios;
    }
    
}
