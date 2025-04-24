/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.consulta_reportes;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.ReporteServicioCitas;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import com.mycompany.salondebellezabe.repositorio.citas.CitaDAO;
import com.mycompany.salondebellezabe.repositorio.servicios.ServicioDAO;
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
public class ReporteServicio extends Repositorio{
    
    private boolean servicioMasReservado = true;
    
    public List<ReporteServicioCitas> obtenerServicioMasReservado(Consulta consulta){
        List<ReporteServicioCitas> reportes = new ArrayList<>();
        String query = armarConsulta(consulta);
        obtenerConeccion();
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            colocarFechas(consulta, stmt);
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    ReporteServicioCitas reporte = new ReporteServicioCitas();
                    reporte.setTotalCitas(result.getInt("total_citas"));
                    ServicioDAO repoServicio = new ServicioDAO();
                    repoServicio.setConeccion(coneccion);
                    Optional<Servicio> posibleServicio = repoServicio.obtenerPorIDSinEmpleados(result.getInt("idServicio"));
                    Servicio servicio = posibleServicio.orElseThrow(
                            () -> new NotFoundException("error al conseguir la informacion de los servicios"));
                    reporte.setServicio(servicio);
                    String query2 = armarConsultaCitas(consulta);
                    try(PreparedStatement stmt2 = coneccion.prepareStatement(query2)){
                        stmt2.setInt(indice++, reporte.getServicio().getIdServicio());
                        colocarFechas(consulta, stmt2);
                        try(ResultSet result2 = stmt2.executeQuery()){
                            List<Cita> citas = new ArrayList<>();
                            while(result2.next()){
                                CitaDAO repoCita = new CitaDAO();
                                repoCita.setConeccion(coneccion);
                                Optional<Cita> posibleCita = repoCita.obtenerPorID(result.getInt("idCita"));
                                Cita cita = posibleCita.orElseThrow(
                                        () -> new NotFoundException("error al conseguir la informacion de las citas"));
                                citas.add(cita);
                            }
                            reporte.setCitas(citas);
                        }
                    }
                    reportes.add(reporte);
                }
            }
        } catch (SQLException e) {
            throw new InvalidDataException("Error al conseguir el reporte intentar mas tarde");
        } finally {
            cerrar();
        }
        return reportes;
    }
    
    public List<ReporteServicioCitas> obtenerServicioMenosReservado(Consulta consulta){
        servicioMasReservado = false;
        return obtenerServicioMasReservado(consulta);
    }

    private String armarConsulta(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*) as total_citas, idServicio FROM Cita ");
        colocarFechas(consulta, query);
        query.append("GROUP BY(idServicio) ORDER BY(total_citas) ");
        String orden = servicioMasReservado ? "DESC ": "ASC ";
        query.append(orden);
        query.append("LIMIT 5 ");
        return query.toString();
    }

    private void colocarFechas(Consulta consulta, StringBuilder query) {
        if (consulta.tieneAmbas()) {
            query.append("WHERE fecha >= ? AND fecha <= ? ");
        } else if (consulta.tieneFechaInicio()) {
            query.append("WHERE fecha >= ? ");
        } else if (consulta.tieneFechaFin()) {
            query.append("WHERE fecha <= ? ");
        }
    }

    private String armarConsultaCitas(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT idCita WHERE idServicio = ? ");
        if (consulta.tieneFechaInicio()) {
            query.append("AND fecha >= ? ");
        }
        if (consulta.tieneFechaFin()) {
            query.append("AND fecha <= ? ");
        }
        return query.toString();
    }
}
