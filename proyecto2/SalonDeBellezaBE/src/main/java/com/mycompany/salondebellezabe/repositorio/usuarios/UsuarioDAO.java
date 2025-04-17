/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.usuarios;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.modelos.enums.Rol;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import com.mycompany.salondebellezabe.repositorio.BusquedaPorAtributo;
import java.sql.JDBCType;

/**
 *
 * @author rafael-cayax
 */
public class UsuarioDAO extends ClaseDAO<Usuario, Long> implements BusquedaPorAtributo<Usuario> {

    private boolean obtenerContraseña = false;
    private boolean obtenerDatos = false;

    /**
     * metodo usado para crear ingresar los datos necesarios para un usuario
     * @param usuario datos del usuario que se va a insertar
     * @apiNote es necesario setear la coneccion para poder insertar datos
     */
    @Override
    public void insertar(Usuario usuario) {
        obtenerConeccion();
        boolean empleado = usuario.getRol() == Rol.EMPLEADO;
        String query = "INSERT INTO Usuario(nombre, correo, rol, contraseña, dpi, telefono, activo";
        query += empleado ? ", descripcion) ": ") ";
        query += "VALUES (?, ?, ?, ?, ?, ?, ?";
        query += empleado ? ", ?)": ")";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            coneccion.setAutoCommit(false);
            stmt.setString(1, usuario.getNombre().trim().replaceAll("\\s+", " "));
            stmt.setString(2, usuario.getCorreo().trim());
            stmt.setString(3, usuario.getRol().name());
            stmt.setString(4, usuario.getContraseña());
            stmt.setLong(5, usuario.getDpi());
            stmt.setString(6, usuario.getTelefono().trim().replaceAll("\\s+", " "));
            stmt.setBoolean(7, usuario.isActivo());
            if (empleado) {
                stmt.setString(8, usuario.getDescripcion());
            }
            stmt.executeUpdate();
            if (usuario.getRol() == Rol.EMPLEADO) {
                FotografiaUsuarioDAO repFoto = new FotografiaUsuarioDAO(usuario.getDpi());
                repFoto.setConeccion(coneccion);
                repFoto.insertar(usuario.getFoto());
            }
            coneccion.commit();
        } catch (SQLException e) {
            regresar();
            e.printStackTrace();
            if (e.getErrorCode() == 1062) {
                throw new InvalidDataException("dpi o correo ya ingresados verificar los datos ingresados");
            }
            throw new InvalidDataException("datos ingresados no validos");
        } finally {
            cerrar();
        }
    }

    /**
     * metodo usado para cambiar el estado de un usuario de activo a desativado
     * en la base de datos 
     * @param dpi el dpi registrado en la base de datos del usuario
     * @throws NotFoundException en caso de no encontrar al usuario
     * @throws InvalidDataException en caso de el id ingresado no sea valido
     */
    @Override
    public void eliminar(Long dpi) {
        obtenerConeccion();
        String query = "UPDATE Usuario SET activo = 0 WHERE dpi = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setLong(1, dpi);
            if (stmt.executeUpdate() <= 0) {
                throw new NotFoundException("no se pudo encontrar al usuario con dpi: '" + dpi + "'");
            }
        } catch (SQLException e) {
            throw new InvalidDataException("el dpi: '" + dpi +"' ingresado no es valido");
        } finally {
            cerrar();
        }
    }

    /**
     * metodo para recuperar los datos del usuario por medio del id 
     * @param dpi numero de dpi registrado en la base de datos
     * @return optional que contiene al posible usuario
     */
    @Override
    public Optional<Usuario> obtenerPorID(Long dpi) {
        String query = "SELECT * FROM Usuario WHERE dpi = ?";
        return buscar(query, dpi, JDBCType.BIGINT);
    }

    /**
     * metodo para actualizar los datos del usuario
     * @param usuario los datos del usuario
     */
    @Override
    public void actualizar(Usuario usuario) {
        obtenerConeccion();
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
            stmt.setLong(7, usuario.getDpi());
            if (stmt.executeUpdate() <= 0) {
                //mandar error sobre usuario no encontrado
            }
        } catch (SQLException e) {
            //mandar error sobre ingresar datos validos
        } finally {
            cerrar();
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
     * @apiNote es necesario settear la coneccion
     */
    @Override
    public Optional<Usuario> buscarPorAtributo(String correo) {
        String query = "SELECT * FROM Usuario WHERE correo = ?";
        return buscar(query, correo.trim(), JDBCType.VARCHAR);
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
        usuario.setDpi(result.getLong("dpi"));
        usuario.setNombre(result.getString("nombre"));
        usuario.setRol(Rol.valueOf(result.getString("rol")));
        if (obtenerContraseña) {
            usuario.setCorreo(result.getString("correo"));
            usuario.setContraseña(result.getString("contraseña"));
        }
        if (obtenerDatos) {
            usuario.setGustos(result.getString("gustos"));
            usuario.setHobbies(result.getString("hobbies"));
            usuario.setDireccion(result.getString("direccion"));
            usuario.setDescripcion(result.getString("descripcion"));
            usuario.setTelefono(result.getString("telefono"));
        }
        usuario.setActivo(result.getBoolean("activo"));
        usuario.setListaNegra(result.getBoolean("listaNegra"));
        return usuario;
    }

    /**
     * metodo usado para actualizar la contraseña del usuario
     * @param usuario usuario con la contraseña actual
     */
    public void actualizarContraseña(Usuario usuario){
        obtenerConeccion();
        String query = "UPDATE Usuario SET Contraseña = ? WHERE dpi = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setString(1, usuario.getContraseña());
            stmt.setLong(2, usuario.getDpi());
            if (stmt.executeUpdate() <= 0) {
                //usuario no encontrado
            }
        } catch (SQLException e) {
            //id de usuario ingresado invalido
        } finally {
            cerrar();
        }
    }

    /**
     * metodo usado para ingresar los datos secundarios del usuario como direccion
     * descripcion, gustos, hobbies y su foto de perfil
     * @param usuario los datos del usuario que se mencionaron anteriormente
     * @throws NotFoundException si no se encuentra al usuario
     * @throws InvalidDataException si alguno de los datos ingresados no son validos
     */
    public void ingresarDetalles(Usuario usuario){
        obtenerConeccion();
        String query = "UPDATE Usuario SET direccion = ?, descripcion = ?, gustos = ?, hobbies = ? WHERE dpi = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            coneccion.setAutoCommit(false);
            stmt.setString(1, usuario.getDireccion().trim().replaceAll("\\s+", " "));
            stmt.setString(2, usuario.getDescripcion());
            stmt.setString(3, usuario.getGustos());
            stmt.setString(4, usuario.getHobbies());
            stmt.setLong(5, usuario.getDpi());
            if (stmt.executeUpdate() <= 0) {
                throw new NotFoundException("cliente no encontrado intente de nuevo");
            }
            FotografiaUsuarioDAO foto = new FotografiaUsuarioDAO(usuario.getDpi());
            foto.setConeccion(coneccion);
            foto.insertar(usuario.getFoto());
            this.setConeccion(coneccion);
            reactivarUsuario(usuario.getDpi());
            this.reiniciarEstado();
            coneccion.commit();
        } catch (SQLException e) {
            regresar();
            throw new InvalidDataException("datos ingresados no validos");
        } finally {
            cerrar();
        }
    }
    
    /**
     * enviar true solo para poder tener acceso a la contraseña del usuario
     * @param obtenerContraseña true para obtener la contraseña del usuario en los
     * datos
     */
    public void setObtenerContraseña(boolean obtenerContraseña) {
        this.obtenerContraseña = obtenerContraseña;
    }

    /**
     * metodo usado para obtener los datos del usuario como descripcion, gustos,
     * hobbies, gustos y telefono, por defecto solo se obtine lo esencial dpi,
     * correo, nombre, rol si esta activo y si esta en la lista negra
     * @param obtenerDatos true para obtener los datos
     */
    public void setObtenerDatos(boolean obtenerDatos) {
        this.obtenerDatos = obtenerDatos;
    }

    /**
     * metodo usado para retornar la lista con todos los clientes que esten en la
     * lista negra
     * @return 
     */
    public List<Usuario> obtenerClientesListaNegra(){
        return listarPorAtributos("SELECT * FROM Usuario WHERE rol = 'CLIENTE' AND listaNegra = TRUE");
        
    }
    
    /**
     * metodo para obtener a todos los usuarios que formen parte del personal del
     * salon de belleza
     * @return lista con el personal del salon
     */
    public List<Usuario> obtenerPersonal(){
        return listarPorAtributos("SELECT * FROM Usuario WHERE rol != 'CLIENTE'");
    }
    
    /**
     * metodo usado para obtener a todos los usuario con el rol de empleado
     * @return una lista de empleados
     */
    public List<Usuario> obtenerEmpleados(){
        return listarPorAtributos("SELECT * FROM Usuario WHERE rol = 'EMPLEADO' AND activo = TRUE");
    }
    
    public void reactivarUsuario(Long dpi){
        obtenerConeccion();
        String query = "UPDATE Usuario SET activo = TRUE WHERE dpi = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setLong(1, dpi);
            if (stmt.executeUpdate() <= 0) {
                throw new NotFoundException("no se encontro al usuario con pdi: '" + dpi + "'");
            }
        } catch (SQLException e) {
            regresar();
            throw new InvalidDataException("ingresar un dpi valido");
        } finally {
            cerrar();
        }
    }
}
