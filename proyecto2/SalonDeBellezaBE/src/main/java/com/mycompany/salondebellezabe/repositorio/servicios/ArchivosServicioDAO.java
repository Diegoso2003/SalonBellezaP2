/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.servicios;

import com.mycompany.salondebellezabe.modelos.ArchivosServicio;
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
public class ArchivosServicioDAO extends ClaseDAO<ArchivosServicio, Integer>{

    private boolean catalogo = false;

    /**
     * metodo usado para insertar los archivos de un servicio a la base de datos
     * @param archivos los archivos del servicio
     */
    @Override
    public void insertar(ArchivosServicio archivos) {
        obtenerConeccion();
        String query = "INSERT INTO ArchivosServicio(idArchivos, catalogo, fotografia, extension) "
                + "VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, archivos.getIdArchivos());
            stmt.setBlob(2, archivos.getCatalogo());
            stmt.setBlob(3, archivos.getFoto().getFoto());
            stmt.setString(4, archivos.getFoto().getExtension());
            if (stmt.executeUpdate() <= 0) {
                //error al ingresar el archivo
            }
        } catch (SQLException e) {
            //error al insertat la imagen o el pdf
        } finally {
            cerrar();
        }
    }

    @Override
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo usado para obtener los archivos de un servicio por medio del id
     * @param id el id del archivo
     * @return un optional con el posible archivo de ser encontrado
     */
    @Override
    public Optional<ArchivosServicio> obtenerPorID(Integer id) {
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT ");
        if (catalogo) {
            consulta.append("catalago ");
        } else {
            consulta.append("fotografia , extension");
        }
        consulta.append("FROM ArchivosServicio WHERE idArchivo = ?");
        return buscar(consulta.toString(), id, JDBCType.INTEGER);
    }

    /**
     * metodo usado para actualizar los archivos del servicio, por defecto solo
     * actualizara la imagen, para actualizar el catalogo use el setter de la 
     * variable catalogo para ponerlo como true
     * @param archivos los datos del archivo del servicio
     */
    @Override
    public void actualizar(ArchivosServicio archivos) {
        String query;
        obtenerConeccion();
        if (catalogo) {
            query = "UPDATE ArchivosServicio SET catalogo = ? WHERE idArchivo = ?";
        } else {
            query = "UPDATE ArchivosServicio SET fotografia = ?, extension = ? WHERE idArchivo = ?";
        }
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            int posicion;
            if (catalogo) {
                stmt.setBlob(1, archivos.getCatalogo());
                posicion = 2;
            } else {
                stmt.setBlob(1, archivos.getFoto().getFoto());
                stmt.setString(2, archivos.getFoto().getExtension());
                posicion = 3;
            }
            stmt.setInt(posicion, archivos.getIdArchivos());
            if (stmt.executeUpdate() <= 0) {
                //error servicio no encontrado
            }
        } catch (SQLException e) {
            //archivo enviado invalidos
        } finally {
            cerrar();
        }
    }

    @Override
    public List<ArchivosServicio> obtenerTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo para obtener los datos del archivo del resultSet de una consulta
     * @param result datos de la consulta
     * @return si catalogo es false entonces recupera la foto, si es true recupera
     * el catalogo
     * @throws SQLException 
     */
    @Override
    protected ArchivosServicio obtenerDatos(ResultSet result) throws SQLException {
        ArchivosServicio archivo = new ArchivosServicio();
        if (catalogo) {
            archivo.setCatalogoBytes(result.getBytes("catalogo"));
        } else {
            Fotografia foto = new Fotografia();
            foto.setFotografia(result.getBytes("fotografia"));
            foto.setExtension(result.getString("extension"));
            archivo.setFoto(foto);
        }
        archivo.setIdArchivos(result.getInt("idArchivo"));
        return archivo;
    }

    /**
     * ingresar true para actualizar el catalogo del servicio, false para la imagen
     * por defecto al usar el metodo actualizar se actualizara la imagen
     * @param catalogo 
     */
    public void setCatalogo(boolean catalogo) {
        this.catalogo = catalogo;
    }

}
