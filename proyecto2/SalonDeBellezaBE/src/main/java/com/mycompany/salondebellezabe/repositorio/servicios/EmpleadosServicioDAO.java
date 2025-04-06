/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.servicios;

import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class EmpleadosServicioDAO extends Repositorio<Usuario, Long>{

    private final Servicio servicio;
    
    /**
     * clase para insertar obtener y eliminar los empleados asignados a un servicio
     * @param servicio servicio en el cual se va a trabajar
     */
    public EmpleadosServicioDAO(Servicio servicio) {
        this.servicio = servicio;
    }

    /**
     * metodo para asociar un empleado a un servicio mediante el dpi del empleado
     * @param empleado el empleado que podra atender el servicio
     */
    @Override
    public void insertar(Usuario empleado) {
        String query = "INSERT INTO EmpleadosServicio(empleado, servicio) VALUES(?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setLong(1, empleado.getDpi());
            stmt.setInt(2, servicio.getIdServicio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                //error empleado ya asignado a este servicio
            } else if (e.getErrorCode() == 1452) {
                // empleado o servicio no encontrados
            }
            // valores ingresados invalidos
        }
    }

    /**
     * metodo para eliminar a un empleado de un servicio para que ya no pueda
     * atender a los clientes que soliciten el servicio
     * @param id el dpi del empleado
     */
    @Override
    public void eliminar(Long id) {
        String query = "DELETE FROM EmpleadosServicio WHERE empleado = ? AND servicio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setLong(1, id);
            stmt.setInt(2, servicio.getIdServicio());
            if (stmt.executeUpdate() <= 0) {
                //empleado no asignado a este servicio
            }
        } catch (SQLException e) {
            //valores ingresados invalidos
        }
    }

    /**
     * metodo para obtener los datos de un empleado asociado a un servicio
     * @param dpi el dpi del empleado
     * @return los datos del empleado
     */
    @Override
    public Optional<Usuario> obtenerPorID(Long dpi) {
        String query = "SELECT * FROM EmpleadosServicio WHERE empleado = ? AND servicio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setLong(1, dpi);
            stmt.setInt(2, servicio.getIdServicio());
            try(ResultSet result = stmt.executeQuery()){
                if (result.next()) {
                    Usuario usuario = obtenerDatos(result);
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            //valores ingresados invalidos
        }
        return Optional.empty();
    }

    @Override
    public void actualizar(Usuario entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * metodo usado para obtener a todos los empleados junto con su informacion
     * que esten asociados al servicio
     * @return 
     */
    @Override
    public List<Usuario> obtenerTodo() {
        List<Usuario> empleados = new ArrayList<>();
        String query = "SELECT * FROM EmpleadosServicio WHERE servicio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, servicio.getIdServicio());
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    Usuario usuario = obtenerDatos(result);
                    empleados.add(usuario);
                }
            }
        } catch (SQLException e) {
            //informar de un error
        }
        return empleados;
    }

    /**
     * metodo usado para obtener los datos de un usuario que este asociado al 
     * servicio
     * @param result los datos de la consulta
     * @return un usuario asociado al servicio
     * @throws SQLException 
     */
    @Override
    protected Usuario obtenerDatos(ResultSet result) throws SQLException {
        Long dpi = result.getLong("empleado");
        UsuarioDAO repositorio = new UsuarioDAO();
        Optional<Usuario> usuario = repositorio.obtenerPorID(dpi);
        return usuario.get();
    }
    
}
