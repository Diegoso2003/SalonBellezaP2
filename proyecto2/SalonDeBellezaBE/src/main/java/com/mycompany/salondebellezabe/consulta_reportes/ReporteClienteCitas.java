/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.consulta_reportes;

import com.mycompany.salondebellezabe.excepciones.ConeccionException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.CitasCliente;
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
public class ReporteClienteCitas extends Repositorio{
    private boolean clienteMasCitas = true;
    
    public List<CitasCliente> clienteConMasCitas(Consulta consulta){
        List<CitasCliente> reportes = new ArrayList<>();
        String query = armarConsulta(consulta);
        obtenerConeccion();
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            colocarFechas(consulta, stmt);
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    CitasCliente reporte = new CitasCliente();
                    reporte.setTotalCitas(result.getInt("totalCitas"));
                    UsuarioDAO repoUsuario = new UsuarioDAO();
                    repoUsuario.setConeccion(coneccion);
                    Optional<Usuario> posibleCliente = repoUsuario.obtenerPorID(result.getLong("cliente"));
                    Usuario cliente = posibleCliente.orElseThrow(() -> new NotFoundException("error al conseguir los datos del cliente"));
                    reporte.setCliente(cliente);
                    String query2 = armarConsultaCitas(consulta);
                    try(PreparedStatement stmt2 = coneccion.prepareStatement(query2)){
                        indice = 1;
                        stmt2.setLong(indice++, reporte.getCliente().getDpi());
                        colocarFechas(consulta, stmt2);
                        try(ResultSet result2 = stmt2.executeQuery()){
                            List<Cita> citas = new ArrayList<>();
                            CitaDAO repoCitas = new CitaDAO();
                            repoCitas.setConeccion(coneccion);
                            while(result2.next()){
                                Optional<Cita> posibleCita = repoCitas.obtenerPorID(result2.getInt("idCita"));
                                Cita cita = posibleCita.orElseThrow(() -> new NotFoundException("error al encontrar los detalles de la cita"));
                                citas.add(cita);
                            }
                            reporte.setCitas(citas);
                        }
                    }
                    reportes.add(reporte);
                }
            }
        } catch (SQLException e) {
            throw new ConeccionException();
        } finally {
            cerrar();
        }
        return reportes;
    }
    
    public List<CitasCliente> clienteConMenosCitas(Consulta consulta){
        clienteMasCitas = false;
        return clienteConMasCitas(consulta);
    }

    private String armarConsulta(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*) AS totalCitas, cliente FROM Cita ");
        colocarFechas(consulta, query);
        query.append("GROUP BY(cliente) ORDER BY(totalCitas) ");
        String orden = clienteMasCitas ? "DESC ": "ASC ";
        query.append(orden).append("LIMIT 5 ");
        return query.toString();
    }

    private void colocarFechas(Consulta consulta, StringBuilder query) {
        if (consulta.tieneAmbas()) {
            query.append("WHERE fecha >= ? AND fecha <= ? ");
        } else if(consulta.tieneFechaInicio()){
            query.append("WHERE fecha >= ? ");
        } else if(consulta.tieneFechaFin()){
            query.append("WHERE fecha <= ? ");
        }
    }

    private String armarConsultaCitas(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT idCita FROM Cita WHERE cliente = ? ");
        if (consulta.tieneFechaInicio()) {
            query.append("AND fecha >= ? ");
        }
        if (consulta.tieneFechaFin()) {
            query.append("AND fecha <= ? ");
        }
        return query.toString();
    }
}
