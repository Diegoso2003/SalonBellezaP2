/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.usuarios;

import com.mycompany.salondebellezabe.modelos.FotografiaUsuario;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class FotografiaUsuarioDAO extends Repositorio<FotografiaUsuario, Long>{

    /**
     * metodo usado para insertar la foto del usuario en la base de datos
     * @param foto foto del usuario
     */
    @Override
    public void insertar(FotografiaUsuario foto) {
        String query = "INSERT INTO FotoUsuario(foto, dpi, extension) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setBlob(1, foto.getFoto());
            stmt.setLong(2, foto.getDpi());
            stmt.setString(3, foto.getExtension());
            if (stmt.executeUpdate() <= 0) {
                //error al insertar
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                //error al asignar la foto
            }
            //foto enviada invalida
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
    public Optional<FotografiaUsuario> obtenerPorID(Long dpi) {
        String query = "SELECT * FROM FotoUsuario WHERE dpi = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setLong(1, dpi);
            try(ResultSet result = stmt.executeQuery()){
                if (result.next()) {
                    return Optional.of(obtenerDatos(result));
                }
            }
        } catch (SQLException e) {
            //mensaje de id invalido
        }
        return Optional.empty();
    }

    /**
     * metodo usado para actualizar la foto del usuario
     * @param foto la nueva foto de perfil
     */
    @Override
    public void actualizar(FotografiaUsuario foto) {
        String query = "UPDATE FotoUsuario SET foto = ?, extension = ? WHERE idUsuario = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setBlob(1, foto.getFoto());
            stmt.setString(2, foto.getExtension());
            stmt.setLong(3, foto.getDpi());
            if (stmt.executeUpdate() <= 0) {
                //no se encontro al usuario
            }
        } catch (SQLException e) {
            //foto ingresada invalida
        }
    }

    @Override
    public List<FotografiaUsuario> obtenerTodo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo para obtener los datos de la foto de perfil
     * @param result con los datos
     * @return la foto con sus datos
     * @throws SQLException 
     */
    @Override
    protected FotografiaUsuario obtenerDatos(ResultSet result) throws SQLException {
        FotografiaUsuario foto = new FotografiaUsuario();
        foto.setDpi(result.getLong("dpi"));
        foto.setFotografia(result.getBytes("foto"));
        foto.setExtension(result.getString("extension"));
        return foto;
    }

}
