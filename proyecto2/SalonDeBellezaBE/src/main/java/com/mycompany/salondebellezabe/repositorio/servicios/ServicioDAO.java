/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.servicios;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.repositorio.BusquedaPorAtributo;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class ServicioDAO extends ClaseDAO<Servicio, Integer> implements BusquedaPorAtributo<Servicio>{

    private boolean obtenerEmpleados = false;
    
    /**
     * metodo usado para guardar la siguiente informacion del servicio:
     * nombre, precio, duracion y descripcion para guardar el pdf y la imagen
     * usar la clase ArchivosServicioDAO
     * @param servicio con los datos antes mencionados
     */
    @Override
    public void insertar(Servicio servicio) {
        obtenerConeccion();
        String query = "INSERT INTO Servicio(nombreServicio, precio, duracion, descripcion) "
                + "VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            coneccion.setAutoCommit(false);
            stmt.setString(1, servicio.getNombreServicio().trim().replaceAll("\\s+", " "));
            stmt.setDouble(2, servicio.getPrecio());
            stmt.setTime(3, Time.valueOf(servicio.getDuracion()));
            stmt.setString(4, servicio.getDescripcion());
            if (stmt.executeUpdate() > 0) {
                try (ResultSet result = stmt.getGeneratedKeys()){
                    if (result.next()) {
                        idGenerado = result.getInt(1);
                        servicio.setIdServicio(idGenerado);
                        ArchivosServicioDAO repoArchivos = new ArchivosServicioDAO();
                        repoArchivos.setConeccion(coneccion);
                        servicio.getArchivos().setIdArchivos(idGenerado);
                        repoArchivos.insertar(servicio.getArchivos());
                        EmpleadosServicioDAO repoEmpleados = new EmpleadosServicioDAO(servicio);
                        repoEmpleados.setConeccion(coneccion);
                        for(Usuario empleado : servicio.getEmpleados()){
                            repoEmpleados.insertar(empleado);
                        }
                        coneccion.commit();
                    }
                } 
            }
        } catch (SQLException e) {
            regresar();
            if (e.getErrorCode() == 1062) {
                throw new InvalidDataException("nombre de servicio: '" + servicio.getNombreServicio() + "' ya registrado en el sistema");
            }
            throw new InvalidDataException("datos ingresados invalidos");
        } finally {
            cerrar();
        }
    }

    /**
     * metodo usado para poder cambiar el estado de un servicio para que ya no se
     * muestre
     * @param id el id del servicio 
     */
    @Override
    public void eliminar(Integer id) {
        obtenerConeccion();
        String query = "UPDATE Servicio SET activo = FALSE WHERE idServicio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, id);
            if (stmt.executeUpdate() <= 0) {
                //mandar error de servicio no encontrado
            }
        } catch (SQLException e) {
            //mandar error de id ingresado invalido
        } finally {
            cerrar();
        }
    }

    /**
     * metodo usado para poder obtente el servicio usando el id del servicio
     * @param id el id del servicio
     * @return un optional con el servicio si es encontrado
     */
    @Override
    public Optional<Servicio> obtenerPorID(Integer id) {
        String query = "SELECT * FROM Servicio WHERE idServicio = ?";
        obtenerEmpleados = true;
        return buscar(query, id, JDBCType.INTEGER);
    }

    @Override
    public void actualizar(Servicio servicio) {
        obtenerConeccion();
        String query = "UPDATE Servicio SET nombreServicio = ? , precio = ?, "
                + "duracion = ?, descripcion = ? WHERE idServicio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setString(1, servicio.getNombreServicio());
            stmt.setDouble(2, servicio.getPrecio());
            stmt.setTime(3, Time.valueOf(servicio.getDuracion()));
            stmt.setString(4, servicio.getDescripcion());
            stmt.setInt(5, servicio.getIdServicio());
            if (stmt.executeUpdate() <= 0) {
                //error servicio no encontrado
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                //error el servicio con el nombre ya esta ingresado en la base de datos
            }
            // error datos ingresados invalidos
        } finally {
            cerrar();
        }
    }

    /**
     * metodo usado para obtener todos los servicios
     * @return una lista con los servicios registrados en el sistema
     */
    @Override
    public List<Servicio> obtenerTodo() {
        return listarPorAtributos("SELECT * FROM Servicio");
    }

    /**
     * metodo usado para obtener los datos de un servicio
     * @param result los datos del servicio
     * @return el servicio
     * @throws SQLException 
     */
    @Override
    protected Servicio obtenerDatos(ResultSet result) throws SQLException {
        Servicio servicio = new Servicio();
        servicio.setNombreServicio(result.getString("nombreServicio"));
        servicio.setIdServicio(result.getInt("idServicio"));
        servicio.setDescripcion(result.getString("descripcion"));
        servicio.setDuracion(result.getTime("duracion").toLocalTime());
        servicio.setPrecio(result.getDouble("precio"));
        servicio.setActivo(result.getBoolean("activo"));
        if (obtenerEmpleados) {
            EmpleadosServicioDAO repositorio = new EmpleadosServicioDAO(servicio);
            List<Usuario> empleados = repositorio.obtenerEmpleadosActivos();
            servicio.setEmpleados(new HashSet<>(empleados));
        }
        return servicio;
    }

    /**
     * metodo usado para encontrar servicio por nombre
     * @param nombre el nombre del servicio
     * @return un optional que tiene el servicio de ser encontrado
     */
    @Override
    public Optional<Servicio> buscarPorAtributo(String nombre) {
        String query = "SELECT * FROM Servicio WHERE nombreServicio = ?";
        return buscar(query, nombre, JDBCType.VARCHAR);
    }
    
    /**
     * metodo usado para obtener todos los servicios disponibles para agendar citas
     * @return la lista con los servicios disponibles
     */
    public List<Servicio> obtenerServiciosActivos(){
        return listarPorAtributos("SELECT * FROM Servicio WHERE activo = TRUE");
    }
}
