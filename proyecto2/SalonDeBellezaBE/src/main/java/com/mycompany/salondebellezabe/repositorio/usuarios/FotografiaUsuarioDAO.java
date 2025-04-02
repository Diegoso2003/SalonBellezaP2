/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.usuarios;

import com.mycompany.salondebellezabe.modelos.FotografiaUsuario;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class FotografiaUsuarioDAO extends Repositorio<FotografiaUsuario, Integer>{

    public FotografiaUsuarioDAO(Connection coneccion) {
        super(coneccion);
    }

    /**
     * metodo usado para insertar la foto del usuario en la base de datos
     * @param foto foto del usuario
     */
    @Override
    public void insertar(FotografiaUsuario foto) {
        String query = "INSERT INTO FotoUsuario(foto, idUsuario, extension) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setBlob(1, foto.getFoto());
            stmt.setInt(2, foto.getIdUsuario());
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
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo para obtener la foto de perfil del usuario guaradada en la base de
     * datos
     * @param id el id del usuario
     * @return optional de la foto del usuario si existe
     */
    @Override
    public Optional<FotografiaUsuario> obtenerPorID(Integer id) {
        String query = "SELECT * FROM FotoUsuario WHERE idUsuario = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, id);
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
            stmt.setInt(3, foto.getIdUsuario());
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
        foto.setIdUsuario(result.getInt("idUsuario"));
        foto.setFotografia(result.getBytes("foto"));
        foto.setExtension(result.getString("foto"));
        return foto;
    }

}
