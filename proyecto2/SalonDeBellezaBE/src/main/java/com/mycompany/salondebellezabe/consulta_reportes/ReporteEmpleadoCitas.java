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
public class ReporteEmpleadoCitas extends Repositorio{
    
    public List<EmpleadoInforme> obtenerEmpleadoConMasCitas(Consulta consulta){
        List<EmpleadoInforme> informes = new ArrayList<>();
        String query = armarConsulta(consulta);
        obtenerConeccion();
        CitaDAO repoCitas = new CitaDAO();
        repoCitas.setConeccion(coneccion);
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            if (consulta.tieneCampo()) {
                stmt.setLong(indice++, Long.parseLong(consulta.getCampo()));
            }
            colocarFechas(consulta, stmt);
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    EmpleadoInforme informe = new EmpleadoInforme();
                    informe.setTotalCitas(result.getInt("totalCitas"));
                    UsuarioDAO repoUsuario = new UsuarioDAO();
                    repoUsuario.setConeccion(coneccion);
                    Optional<Usuario> posibleUsuario = repoUsuario.obtenerPorID(result.getLong("empleado"));
                    Usuario empleado = posibleUsuario.orElseThrow(() -> new NotFoundException("error al conseguir los datos del empleado"));
                    informe.setEmpleado(empleado);
                    double total = 0;
                    String query2 = armarConsultaCitas(consulta);
                    indice = 1;
                    try(PreparedStatement stmt2 = coneccion.prepareStatement(query2)){
                        stmt2.setLong(indice++, empleado.getDpi());
                        colocarFechas(consulta, stmt2);
                        try(ResultSet result2 = stmt2.executeQuery()){
                            List<Cita> citas = new ArrayList<>();
                            while(result2.next()){
                                Optional<Cita> posibleCita = repoCitas.obtenerPorID(result2.getInt("idCita"));
                                Cita cita = posibleCita.orElseThrow(() -> new NotFoundException("error al conseguir los datos de las citas"));
                                citas.add(cita);
                                total += cita.getCostoTotal();
                            }
                            informe.setCitas(citas);
                            informe.setTotalGanancias(total);
                            informes.add(informe);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new ConeccionException();
        } catch (NumberFormatException e) {
            throw new InvalidDataException("ingresar un dpi valido");
        } finally {
            cerrar();
        }
        return informes;
    }

    private String armarConsulta(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*) AS totalCitas, empleado FROM Cita WHERE estado = 'ATENDIDA' ");
        if (consulta.tieneCampo()) {
            query.append("AND empleado = ? ");
        }
        if (consulta.tieneFechaInicio()) {
            query.append("AND fecha >= ? ");
        }
        if (consulta.tieneFechaFin()) {
            query.append("AND fecha <= ? ");
        }
        query.append("GROUP BY(empleado) ORDER BY(totalCitas) DESC LIMIT 5 ");
        return query.toString();
    }

    private String armarConsultaCitas(Consulta consulta) {
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
