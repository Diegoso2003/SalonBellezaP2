/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.usuarios;

import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.modelos.enums.Rol;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import com.mycompany.salondebellezabe.repositorio.BusquedaPorAtributo;
import java.sql.JDBCType;

/**
 *
 * @author rafael-cayax
 */
public class UsuarioDAO extends Repositorio<Usuario, Integer> implements BusquedaPorAtributo<Usuario> {

    public UsuarioDAO(Connection coneccion) {
        super(coneccion);
    }

    /**
     * metodo usado para insertar solo los datos mas esenciales del usuario
     * @param usuario datos del usuario que se va a insertar
     * @return el id del dato ingresado
     */
    @Override
    public Integer insertar(Usuario usuario) {
        String query = "INSERT INTO Usuario(nombre, correo, rol, contraseña, dpi, telefono, direccion, descripcion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = coneccion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getCorreo());
            statement.setString(3, usuario.getRol().name());
            statement.setString(4, usuario.getContraseña());
            statement.setInt(5, usuario.getDpi());
            statement.setString(6, usuario.getTelefono());
            statement.setString(7, usuario.getDireccion());
            statement.setString(8, usuario.getDescripcion());
            if (statement.executeUpdate() > 0) {
                try(ResultSet result = statement.getGeneratedKeys()){
                    if (result.next()) {
                        return result.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                //mandar mensaje de correo duplicado o dpi duplicado
            }
            //mandar mensaje de datos invalidos
        }
        return 0;
    }

    /**
     * metodo usado para cambiar el estado de un usuario de activo a desativado
     * en la base de datos 
     * @param id el id registrado en la base de datos del usuario
     */
    @Override
    public void eliminar(Integer id) {
        String query = "UPDATE Usuario SET activo = 0 WHERE idUsuario = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, id);
            if (stmt.executeUpdate() <= 0) {
                //mandar mensaje de que no se encontro el usuario con el id
            }
        } catch (SQLException e) {
        }
    }

    /**
     * metodo para recuperar los datos del usuario por medio del id 
     * @param id numero de id registrado en la base de datos
     * @return optional que contiene al posible usuario
     */
    @Override
    public Optional<Usuario> obtenerPorID(Integer id) {
        String query = "SELECT * FROM Usuario WHERE idUsuario = ?";
        return buscar(query, id, JDBCType.INTEGER);
    }

    /**
     * metodo para actualizar los datos del usuario
     * @param usuario los datos del usuario
     */
    @Override
    public void actualizar(Usuario usuario) {
        String query = "UPDATE Usuario SET nombre = ?, telefono = ?, direccion = ?, "
                + "hobbies = IFNULL(?, 'No especificado'), gustos = IFNULL(?, 'No especificado'), "
                + "descripcion = IFNULL(?, 'Sin descripcion') WHERE idUsuario = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getTelefono());
            stmt.setString(3, usuario.getDireccion());
            stmt.setString(4, usuario.getHobbies());
            stmt.setString(5, usuario.getGustos());
            stmt.setString(6, usuario.getDescripcion());
            stmt.setInt(7, usuario.getIdUsuario());
            if (stmt.executeUpdate() <= 0) {
                //mandar error sobre usuario no encontrado
            }
        } catch (SQLException e) {
            //mandar error sobre ingresar datos validos
        }
    }
    
    /**
     * metodo usado para obtener todos los datos de la tabla de usuarios
     * @return lista con los usuarios registrados en el sistema
     */
    @Override
    public List<Usuario> obtenerTodo() {
        return listarPorAtributos("SELECT * FROM Usuario");
    }

    /**
     * metodo usado para poder buscar al usuario por el correo
     * @param correo el correo con el cual esta registrado el usuario
     * @return un optional con los datos del posible usuario
     */
    @Override
    public Optional<Usuario> buscarPorAtributo(String correo) {
        String query = "SELECT * FROM Usuario WHERE correo = ?";
        return buscar(query, correo, JDBCType.VARCHAR);
    }

    /**
     * metodo usado para obtener los datos del result
     * @param result con los datos del usuario
     * @return los datos del usuario
     * @throws SQLException 
     */
    @Override
    protected Usuario obtenerDatos(ResultSet result) throws SQLException{
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(result.getInt("idUsuario"));
        usuario.setNombre(result.getString("nombre"));
        usuario.setCorreo(result.getString("correo"));
        usuario.setRol(Rol.valueOf(result.getString("rol")));
        usuario.setContraseña(result.getString("contraseña"));
        usuario.setDpi(result.getInt("dpi"));
        usuario.setGustos(result.getString("gustos"));
        usuario.setHobbies(result.getString("hobbies"));
        usuario.setDireccion(result.getString("direccion"));
        usuario.setTelefono(result.getString("telefono"));
        usuario.setActivo(result.getBoolean("activo"));
        usuario.setListaNegra(result.getBoolean("listaNegra"));
        usuario.setDescripcion(result.getString("descripcion"));
        return usuario;
    }

    /**
     * metodo usado para actualizar la contraseña del usuario
     * @param usuario usuario con la contraseña actual
     */
    public void actualizarContraseña(Usuario usuario){
        String query = "UPDATE Usuario SET Contraseña = ? WHERE idUsuario = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setString(1, usuario.getContraseña());
            stmt.setInt(2, usuario.getIdUsuario());
            if (stmt.executeUpdate() <= 0) {
                //usuario no encontrado
            }
        } catch (SQLException e) {
            //id de usuario ingresado invalido
        }
    }
}
