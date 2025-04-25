/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.citas;

import com.mycompany.salondebellezabe.SumadorDeHoras;
import com.mycompany.salondebellezabe.consulta_reportes.Consulta;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.modelos.enums.EstadoCita;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;
import com.mycompany.salondebellezabe.repositorio.servicios.ServicioDAO;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class CitaDAO extends ClaseDAO<Cita, Integer>{

    @Override
    public void insertar(Cita cita) {
        obtenerConeccion();
        String query = "INSERT INTO Cita(cliente, empleado, fecha, hora, estado, idServicio)"
                + " VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setLong(1, cita.getCliente().getDpi());
            stmt.setLong(2, cita.getEmpleado().getDpi());
            stmt.setDate(3, Date.valueOf(cita.getFecha()));
            stmt.setTime(4, Time.valueOf(cita.getHora()));
            stmt.setString(5, cita.getEstado().toString());
            stmt.setInt(6, cita.getServicio().getIdServicio());
            if (stmt.executeUpdate() > 0) {
                try(ResultSet result = stmt.getGeneratedKeys()){
                    idGenerado = result.getInt(1);
                }
            }
        } catch (SQLException e) {
            //ingresar valores validos
        } finally {
            cerrar();
        }
    }

    @Override
    public void eliminar(Integer id) {
        String query = "UPDATE Cita SET estado = 'RECHAZADA' WHERE idCita = ?";
        actualizarCita(id, query);
    }

    @Override
    public Optional<Cita> obtenerPorID(Integer id) {
        String query = "SELECT s.idServicio as servicio, nombreServicio, duracion, precio, c.* From Cita c "
                + "INNER JOIN Servicio s ON s.idServicio = c.idServicio WHERE idCita = ?";
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
        return listarPorAtributos("SELECT s.idServicio as servicio, nombreServicio, duracion, precio, c.* From Cita c "
                + "INNER JOIN Servicio s ON s.idServicio = c.idServicio");
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
        cita.setIdCita(result.getInt("idCita"));
        UsuarioDAO repositorioUsuario = new UsuarioDAO();
        repositorioUsuario.setConeccion(coneccion);
        Optional<Usuario> cliente = repositorioUsuario.obtenerPorID(result.getLong("cliente"));
        cita.setCliente(cliente.orElseThrow(() -> new NotFoundException("Error al conseguir la informacion del cliente")));
        Optional<Usuario> empleado = repositorioUsuario.obtenerPorID(result.getLong("empleado"));
        cita.setEmpleado(empleado.orElseThrow(() -> new NotFoundException("Error al conseguir la informacion del empleado")));
        Servicio servicio = new Servicio();
        servicio.setNombreServicio(result.getString("nombreServicio"));
        servicio.setPrecio(result.getDouble("precio"));
        servicio.setIdServicio(result.getInt("idServicio"));
        servicio.setDuracion(result.getTime("duracion").toLocalTime());
        cita.setServicio(servicio);
        SumadorDeHoras sumador = new SumadorDeHoras(cita.getHora(), cita.getServicio().getDuracion());
        cita.setHoraFin(sumador.obtenerSuma());
        return cita;
    }

    /**
     * metodo para actualizar el estado de una cita
     * @param id el id de la cita
     * @param consulta la query a ejecutar
     */
    private void actualizarCita(Integer id, String consulta) {
        obtenerConeccion();
        try (PreparedStatement stmt = coneccion.prepareStatement(consulta)){
            stmt.setInt(1, id);
            if (stmt.executeUpdate() <= 0) {
                //no se encontro la cita
            }
        } catch (SQLException e) {
            //otro error
        } finally {
            cerrar();
        }
    }

    public void citaAtendida(Cita cita) {
        obtenerConeccion();
        String query = "UPDATE Cita SET costoTotal = ?, estado = 'ATENDIDA' WHERE idCita = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setDouble(1, cita.getServicio().getPrecio());
            stmt.setInt(2, cita.getIdCita());
            if (stmt.executeUpdate() <= 0) {
                throw new NotFoundException("cita no encontrada");
            }
        } catch (SQLException e) {
            throw new InvalidDataException("datos enviados no validos");
        } finally {
            cerrar();
        }
    }

    public void citaAusente(Cita cita) {
        obtenerConeccion();
        String query = "UPDATE Cita SET estado = 'AUSENTE' WHERE idCita = ?";
        String query2 = "UPDATE Usuario SET listaNegra = TRUE WHERE dpi = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query);
                PreparedStatement stmt2 = coneccion.prepareStatement(query2)){
            coneccion.setAutoCommit(false);
            stmt.setInt(1, cita.getIdCita());
            if(stmt.executeUpdate() <= 0){
                throw new NotFoundException("cita no encontrada");
            }
            stmt2.setLong(1, cita.getCliente().getDpi());
            if (stmt2.executeUpdate() <= 0) {
                throw new NotFoundException("cliente no encontrado");
            }
        } catch (SQLException e) {
            regresar();
            throw new InvalidDataException("datos enviados invalidos");
        } finally {
            cerrar();
        }
    }

    public List<Cita> obtenerInfoGastosCita(Consulta consulta, Usuario cliente) {
        List<Cita> citas = new ArrayList<>();
        String query = armarConsultaClienteCitas(consulta);
        obtenerConeccion();
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setLong(indice++, cliente.getDpi());
            colocarFechas(consulta, stmt);
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    Cita cita = new Cita();
                    cita.setIdCita(result.getInt("idCita"));
                    cita.setFecha(result.getDate("fecha").toLocalDate());
                    cita.setCostoTotal(result.getDouble("costoTotal"));
                    cita.setHora(result.getTime("hora").toLocalTime());
                    ServicioDAO repoServicio = new ServicioDAO();
                    repoServicio.setConeccion(coneccion);
                    Optional<Servicio> posibleServicio = repoServicio.obtenerPorIDSinEmpleados(result.getInt("idServicio"));
                    Servicio servicio = posibleServicio.orElseThrow(() -> new NotFoundException("error al conseguir datos de servicios"));
                    cita.setServicio(servicio);
                    UsuarioDAO repoUsuario = new UsuarioDAO();
                    repoUsuario.setConeccion(coneccion);
                    Optional<Usuario> posibleEmpleado = repoUsuario.obtenerPorID(result.getLong("empleado"));
                    Usuario empleado = posibleEmpleado.orElseThrow(() -> new NotFoundException("error al conseguir datos de empleado"));
                    cita.setEmpleado(empleado);
                    citas.add(cita);
                }
            }
        } catch (SQLException e) {
            throw new NotFoundException("error al conseguir los datos de la cita");
        } finally {
            cerrar();
        }
        return citas;
    }

    private String armarConsultaClienteCitas(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM Cita WHERE cliente = ? AND estado = 'ATENDIDA' ");
        if (consulta.tieneFechaInicio()) {
            query.append("AND fecha >= ? ");
        }
        if (consulta.tieneFechaFin()) {
            query.append("AND fecha <= ? ");
        }
        return query.toString();
    }
    
}
