/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.consulta_reportes;

import com.mycompany.salondebellezabe.excepciones.ConeccionException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.ServicioGanancias;
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
public class ReporteServicioGanancias extends Repositorio {

    public List<ServicioGanancias> obtenerGananciasServicios(Consulta consulta) {
        List<ServicioGanancias> ganancias = new ArrayList<>();
        ServicioDAO repoServicio = new ServicioDAO();
        List<Servicio> servicios = new ArrayList<>();
        if (consulta.tieneCampo()) {
            Optional<Servicio> posibleServicio = repoServicio.buscarPorAtributo(consulta.getCampo().trim().replaceAll("\\s+", " "));
            Servicio servicio2 = posibleServicio.orElseThrow(() -> new NotFoundException("servicio no encontrado"));
            servicios.add(servicio2);
        } else {
            servicios = repoServicio.obtenerTodo();
        }
        limpiarDetalles(servicios);
        String query = armarConsulta(consulta);
        CitaDAO repoCita = new CitaDAO();
        for (Servicio servicio : servicios) {
            ServicioGanancias ganancia = new ServicioGanancias();
            ganancia.setServicio(servicio);
            double total = 0;
            obtenerConeccion();
            try (PreparedStatement stmt = coneccion.prepareStatement(query)) {
                indice = 1;
                stmt.setInt(indice++, servicio.getIdServicio());
                colocarFechas(consulta, stmt);
                try (ResultSet result = stmt.executeQuery()) {
                    List<Cita> citas = new ArrayList<>();
                    while (result.next()) {
                        repoCita.setConeccion(coneccion);
                        Optional<Cita> posibleCita = repoCita.obtenerPorID(result.getInt("idCita"));
                        Cita cita = posibleCita.orElseThrow(() -> new NotFoundException("error al conseguir los datos de la cita"));
                        citas.add(cita);
                        total += cita.getCostoTotal();
                    }
                    ganancia.setCitas(citas);
                    ganancia.setTotalGanancias(total);
                }
            } catch (SQLException e) {
                throw new ConeccionException();
            } finally {
                cerrar();
            }
            ganancias.add(ganancia);
        }
        ganancias.sort((p1, p2) -> Double.compare(p2.getTotalGanancias(), p1.getTotalGanancias()));
        return ganancias;
    }

    private void limpiarDetalles(List<Servicio> servicios) {
        for (Servicio servicio : servicios) {
            servicio.setDescripcion("");
        }
    }

    private String armarConsulta(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT idCita FROM Cita WHERE idServicio = ? AND estado = 'ATENDIDA' ");
        if (consulta.tieneFechaInicio()) {
            query.append("AND fecha >= ? ");
        }
        if (consulta.tieneFechaFin()) {
            query.append("AND fecha <= ? ");
        }
        return query.toString();
    }
}
