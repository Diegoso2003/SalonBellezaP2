/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio;

import com.mycompany.salondebellezabe.modelos.Cita;
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

    public CitaDAO(Connection coneccion) {
        super(coneccion);
    }

    @Override
    public void insertar(Cita cita) {
        String query = "INSERT INTO Cita(cliente, empleado, fecha, hora, estado)"
                + " VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, cita.getCliente().getDpi());
            stmt.setInt(2, cita.getEmpleado().getDpi());
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    @Override
    public List<Cita> obtenerTodo() {
        return listarPorAtributos("SELECT * FROM Cita");
    }

    @Override
    protected Cita obtenerDatos(ResultSet result) throws SQLException {
        Cita cita = new Cita();
        return cita;
    }
    
}
