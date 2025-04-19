/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.usuarios;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
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
public class FotografiaUsuarioDAO extends ClaseDAO<Fotografia, Long>{

    private Long dpi;

    public FotografiaUsuarioDAO(Long dpi) {
        this.dpi = dpi;
    }
    
    public FotografiaUsuarioDAO() {
    }
    
    /**
     * metodo usado para insertar la foto del usuario en la base de datos
     * @param foto foto del usuario
     */
    @Override
    public void insertar(Fotografia foto) {
        obtenerConeccion();
        String query = "INSERT INTO FotoUsuario(foto, dpi, extension) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setBlob(1, foto.getFoto());
            stmt.setLong(2, dpi);
            stmt.setString(3, foto.getExtension());
            stmt.executeUpdate();
        } catch (SQLException e) {
            regresar();
            switch (e.getErrorCode()) {
                case 1062:
                    throw new InvalidDataException("usuario con dpi: '" + dpi + "' ya tiene una foto, para cambiarla vaya al perfil");
                case 1406:
                    throw new InvalidDataException("la imagen ingresada es demasiado pesada ingrese otra");
                case 1452:
                    throw new NotFoundException("usuario con dpi: '" + dpi + "' no encontrado");
                default:
                    throw new InvalidDataException("datos ingresados invalidos");
            }
        } finally {
            cerrar();
        }
    }

    @Override
    public void eliminar(Long dpi) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo para obtener la foto de perfil del usuario guaradada en la base de
     * datos
     * @param dpi el id del usuario
     * @return optional de la foto del usuario si existe
     */
    @Override
    public Optional<Fotografia> obtenerPorID(Long dpi) {
        String query = "SELECT * FROM FotoUsuario WHERE dpi = ?";
        return buscar(query, dpi, JDBCType.BIGINT);
    }

    /**
     * metodo usado para actualizar la foto del usuario
     * @param foto la nueva foto de perfil
     */
    @Override
    public void actualizar(Fotografia foto) {
        obtenerConeccion();
        String query = "UPDATE FotoUsuario SET foto = ?, extension = ? WHERE idUsuario = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setBlob(1, foto.getFoto());
            stmt.setString(2, foto.getExtension());
            stmt.setLong(3, dpi);
            if (stmt.executeUpdate() <= 0) {
                //no se encontro al usuario
            }
        } catch (SQLException e) {
            //foto ingresada invalida
        } finally {
            cerrar();
        }
    }

    @Override
    public List<Fotografia> obtenerTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo para obtener los datos de la foto de perfil
     * @param result con los datos
     * @return la foto con sus datos
     * @throws SQLException 
     */
    @Override
    protected Fotografia obtenerDatos(ResultSet result) throws SQLException {
        Fotografia foto = new Fotografia();
        foto.setFotografia(result.getBytes("foto"));
        foto.setExtension(result.getString("extension"));
        return foto;
    }

}
