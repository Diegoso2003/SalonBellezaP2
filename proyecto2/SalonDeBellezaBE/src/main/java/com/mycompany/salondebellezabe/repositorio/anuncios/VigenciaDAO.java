/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.anuncios;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Vigencia;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class VigenciaDAO extends ClaseDAO<Vigencia, Integer>{

    @Override
    public void insertar(Vigencia vigencia) {
        obtenerConeccion();
        String query = "INSERT INTO Vigencia(idAnuncio, dias, fechaPublicacion, precio) "
                + "VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, vigencia.getIdAnuncio());
            stmt.setInt(2, vigencia.getDias());
            stmt.setDate(3, Date.valueOf(vigencia.getFechaPublicacion()));
            stmt.setDouble(4, vigencia.getPrecio());
            if (stmt.executeUpdate() > 0) {
                try(ResultSet result = stmt.getGeneratedKeys()){
                    if (result.next()) {
                        idGenerado = result.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            regresar();
            if (e.getErrorCode() == 1452) {
               throw new NotFoundException("no se encontro el anuncio con id: '" + vigencia.getIdAnuncio() + "'");
            }
            throw new InvalidDataException("ingresar correctamente los datos solicitados");
        } finally {
            cerrar();
        }
    }

    @Override
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Vigencia> obtenerPorID(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar(Vigencia entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Vigencia> obtenerTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected Vigencia obtenerDatos(ResultSet result) throws SQLException {
        Vigencia vigencia = new Vigencia();
        vigencia.setIdAnuncio(result.getInt("idAnuncio"));
        vigencia.setIdVigencia(result.getInt("idVigencia"));
        vigencia.setFechaPublicacion(result.getDate("fechaPublicacion").toLocalDate());
        vigencia.setDias(result.getInt("dias"));
        vigencia.setPrecio(result.getDouble("precio"));
        return vigencia;
    }
    
    /**
     * metodo usado para obtener la vigencia mas actual de un anuncio
     * @param idAnuncio el id del anuncio
     * @return la vigencia del anuncio actual
     */
    public Vigencia obtenerVigenciaActualAnuncio(Integer idAnuncio) {
        Optional<Vigencia> posibleVigencia = this.buscar("SELECT * FROM Vigencia WHERE idAnuncio = ? ORDER BY(fechaPublicacion) DESC LIMIT 1", 
                idAnuncio, JDBCType.INTEGER);
        return posibleVigencia.orElseThrow(() -> new NotFoundException("error al encontrar la vigencia del anuncio"));
    }
}
