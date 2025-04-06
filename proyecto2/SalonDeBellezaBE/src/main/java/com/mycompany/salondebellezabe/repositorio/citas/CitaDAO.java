/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.citas;

import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.modelos.enums.EstadoCita;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class CitaDAO extends Repositorio<Cita, Integer>{

    @Override
    public void insertar(Cita cita) {
        String query = "INSERT INTO Cita(cliente, empleado, fecha, hora, estado)"
                + " VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setLong(1, cita.getCliente().getDpi());
            stmt.setLong(2, cita.getEmpleado().getDpi());
            stmt.setDate(3, Date.valueOf(cita.getFecha()));
            stmt.setTime(4, Time.valueOf(cita.getHora()));
            stmt.setString(5, cita.getEstado().toString());
            if (stmt.executeUpdate() > 0) {
                try(ResultSet result = stmt.getGeneratedKeys()){
                    idGenerado = result.getInt(1);
                }
            }
        } catch (SQLException e) {
            //ingresar valores validos
        }
    }

    @Override
    public void eliminar(Integer id) {
        String query = "UPDATE Cita SET estado = 'RECHAZADA' WHERE idCita = ?";
        actualizarCita(id, query);
    }

    @Override
    public Optional<Cita> obtenerPorID(Integer id) {
        String query = "SELECT * FROM Cita WHERE idCita = ?";
        return buscar(query, id, JDBCType.INTEGER);
    }

    @Override
    public void actualizar(Cita entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * selecciona todos las citas con sus datos
     * @return una lista con todas las citas
     */
    @Override
    public List<Cita> obtenerTodo() {
        return listarPorAtributos("SELECT * FROM Cita");
    }

    /**
     * metodo para obtener los datos de la cita que se haya obtenido de la consulta
     * @param result los resultados de la consulta
     * @return la cita con los datos
     * @throws SQLException 
     */
    @Override
    protected Cita obtenerDatos(ResultSet result) throws SQLException {
        Cita cita = new Cita();
        cita.setCostoTotal(result.getDouble("costoTotal"));
        cita.setFecha(result.getDate("fecha").toLocalDate());
        cita.setHora(result.getTime("hora").toLocalTime());
        cita.setEstado(EstadoCita.valueOf(result.getString("estado")));
        UsuarioDAO repositorioUsuario = new UsuarioDAO();
        repositorioUsuario.setConeccion(coneccion);
        Optional<Usuario> cliente = repositorioUsuario.obtenerPorID(result.getLong("cliente"));
        cita.setCliente(cliente.get());
        Optional<Usuario> empleado = repositorioUsuario.obtenerPorID(result.getLong("empleado"));
        cita.setEmpleado(empleado.get());
        return cita;
    }

    /**
     * metodo para actualizar el estado de una cita
     * @param id el id de la cita
     * @param consulta la query a ejecutar
     */
    private void actualizarCita(Integer id, String consulta) {
        try (PreparedStatement stmt = coneccion.prepareStatement(consulta)){
            stmt.setInt(1, id);
            if (stmt.executeUpdate() <= 0) {
                //no se encontro la cita
            }
        } catch (SQLException e) {
            //otro error
        }
    }
    
}
