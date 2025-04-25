/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.consulta_reportes;

import com.mycompany.salondebellezabe.excepciones.ConeccionException;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.EmpleadoInforme;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import com.mycompany.salondebellezabe.repositorio.citas.CitaDAO;
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
public class ReporteEmpleadoGanancias extends Repositorio {

    public List<EmpleadoInforme> obtenerGananciasEmpleado(Consulta consulta) {
        List<EmpleadoInforme> empleadoGanancias = new ArrayList<>();
        List<Usuario> empleados = obtenerEmpleados(consulta);
        String query = armarConsulta(consulta);
        CitaDAO repoCita = new CitaDAO();
        for(Usuario empleado: empleados){
            EmpleadoInforme informe = new EmpleadoInforme();
            double total = 0;
            int totalCitas = 0;
            informe.setEmpleado(empleado);
            obtenerConeccion();
            try (PreparedStatement stmt = coneccion.prepareStatement(query)){
                indice = 1;
                stmt.setLong(indice++, empleado.getDpi());
                colocarFechas(consulta, stmt);
                try(ResultSet result = stmt.executeQuery()){
                    repoCita.setConeccion(coneccion);
                    List<Cita> citas = new ArrayList<>();
                    while(result.next()){
                        Optional<Cita> posibleCita = repoCita.obtenerPorID(result.getInt("idCita"));
                        Cita cita = posibleCita.orElseThrow(() -> new NotFoundException("Error al cargar los datos de la cita"));
                        citas.add(cita);
                        total += cita.getCostoTotal();
                        totalCitas++;
                    }
                    informe.setCitas(citas);
                    informe.setTotalGanancias(total);
                    informe.setTotalCitas(totalCitas);
                    empleadoGanancias.add(informe);
                }
            } catch (SQLException e) {
                throw new ConeccionException();
            } finally {
                cerrar();
            }
        }
        empleadoGanancias.sort((p1, p2) -> Double.compare(p2.getTotalGanancias(), p1.getTotalGanancias()));
        return empleadoGanancias;
    }

    private List<Usuario> obtenerEmpleados(Consulta consulta) {
        UsuarioDAO repoUsuario = new UsuarioDAO();
        if (consulta.tieneCampo()) {
            try {
                Long dpi = Long.valueOf(consulta.getCampo().trim());
                Optional<Usuario> posibleUsuario = repoUsuario.obtenerPorID(dpi);
                Usuario empleado = posibleUsuario.orElseThrow(() -> new NotFoundException("empleado no encontrado"));
                return List.of(empleado);
            } catch (NumberFormatException e) {
                throw new InvalidDataException("Ingresar un dpi valido");
            }
        } else {
            return repoUsuario.obtenerEmpleadosRegistrados();
        }
    }

    private String armarConsulta(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT idCita FROM Cita WHERE empleado = ? AND estado = 'ATENDIDA' ");
        if (consulta.tieneFechaInicio()) {
            query.append("AND fecha >= ? ");
        }
        if (consulta.tieneFechaFin()) {
            query.append("AND fecha <= ? ");
        }
        return query.toString();
    }
}
