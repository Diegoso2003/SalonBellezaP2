/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.servicios;

import com.mycompany.salondebellezabe.modelos.ArchivosServicio;
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
public class ArchivosServicioDAO extends Repositorio<ArchivosServicio, Integer>{

    private boolean catalogo = false;
    
    /**
     * usar para recuperar la foto o el catalogo
     * @param coneccion la coneccion a la base de datos
     * @param catalogo valor para indicar si debe recuperar el catalogo, true para
     * recuperar el catalogo, false para recuperar la imagen
     */
    public ArchivosServicioDAO(Connection coneccion, boolean catalogo) {
        super(coneccion);
        this.catalogo = catalogo;
    }
    
    /**
     * usar para insertar, eliminar o actualizar el catalogo e imagen
     * @param coneccion la coneccion a la base de datos
     */
    public ArchivosServicioDAO(Connection coneccion){
        super(coneccion);
    }

    /**
     * metodo usado para insertar los archivos de un servicio a la base de datos
     * @param archivos los archivos del servicio
     * @return el id con el archivos servicio ingresado aunque en este caso es cero
     */
    @Override
    public Integer insertar(ArchivosServicio archivos) {
        String query = "INSERT INTO ArchivosServicio(idArchivos, catalogo, fotografia, extension) "
                + "VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, archivos.getIdArchivos());
            stmt.setBlob(2, archivos.getCatalogo());
            stmt.setBlob(3, archivos.getFotografia());
            stmt.setString(4, archivos.getExtension());
            if (stmt.executeUpdate() <= 0) {
                //error al ingresar el archivo
            }
        } catch (SQLException e) {
            //error al insertat la imagen o el pdf
        }
        return 0;
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
            consulta.append("foto ");
        }
        consulta.append("FROM ArchivosServicio WHERE idArchivo = ?");
        return buscar(consulta.toString(), id, JDBCType.INTEGER);
    }

    /**
     * metodo exclusivo para actualizar el catalogo del servicio, para la imagen
     * usar el metodo actualizaFoto
     * @param entidad los datos del archivo
     */
    @Override
    public void actualizar(ArchivosServicio entidad) {
        
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
            archivo.setFoto(result.getBytes("fotografia"));
            archivo.setExtension(result.getString("extension"));
        }
        archivo.setIdArchivos(result.getInt("idArchivo"));
        return archivo;
    }

    /**
     * metodo exclusivo para poder actualizar la foto del servicio, para actualizar
     * el catalogo usar el metodo actualizar
     * @param entidad los datos del servicio
     */
    public void actualizarFoto(ArchivosServicio entidad) {
        
    }
    
}
