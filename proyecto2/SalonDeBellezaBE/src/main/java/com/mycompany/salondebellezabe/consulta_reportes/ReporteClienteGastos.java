/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.consulta_reportes;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.GastosCliente;
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
public class ReporteClienteGastos extends Repositorio{
    private boolean clienteMasGasto = true;
    
    public List<GastosCliente> obtenerClienteMasGasto(Consulta consulta){
        List<GastosCliente> reporteGastos = new ArrayList<>();
        String query = armarConsulta(consulta);
        obtenerConeccion();
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            colocarFechas(consulta, stmt);
            if (consulta.tieneCampo()) {
                stmt.setLong(indice, Long.parseLong(consulta.getCampo().trim()));
            }
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    GastosCliente gastos = new GastosCliente();
                    gastos.setGastoTotal(result.getDouble("total_gasto"));
                    UsuarioDAO repoUsuario = new UsuarioDAO();
                    repoUsuario.setConeccion(coneccion);
                    Optional<Usuario> posibleUsuario = repoUsuario.obtenerPorID(result.getLong(("cliente")));
                    Usuario cliente = posibleUsuario.orElseThrow(() -> new NotFoundException("Error al conseguir la informacion del cliente"));
                    gastos.setCliente(cliente);
                    CitaDAO repoCita = new CitaDAO();
                    repoCita.setConeccion(coneccion);
                    List<Cita> citas = repoCita.obtenerInfoGastosCita(consulta, cliente);
                    gastos.setCitas(citas);
                    reporteGastos.add(gastos);
                }
            }
        } catch (SQLException e) {
            System.out.println(query);
            System.out.println(e);
            throw new NotFoundException("error al conseguir los datos de los clientes");
        } catch(NumberFormatException e) {
            throw new InvalidDataException("ingresar un dpi valido");
        }finally {
            cerrar();
        }
        return reporteGastos;
    }
    
    public List<GastosCliente> obtenerClienteMenosGasto(Consulta consulta){
        clienteMasGasto = false;
        return obtenerClienteMasGasto(consulta);
    }

    private String armarConsulta(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT SUM(costoTotal) as total_gasto, cliente FROM Cita ");
        colocarFechas(consulta, query);
        query.append("GROUP BY(cliente) ORDER BY(total_gasto) ");
        String orden = clienteMasGasto ? "DESC ": "ASC ";
        query.append(orden);
        query.append("LIMIT 5 ");
        return query.toString();
    }

    private void colocarFechas(Consulta consulta, StringBuilder query) {
        boolean fechas = false;
        if (consulta.tieneAmbas()) {
            fechas = true;
            query.append("WHERE fecha >= ? AND fecha <= ? ");
        } else if (consulta.tieneFechaInicio()) {
            fechas = true;
            query.append("WHERE fecha >= ? ");
        } else if (consulta.tieneFechaFin()) {
            fechas = true;
            query.append("WHERE fecha <= ? ");
        }
        if (consulta.tieneCampo() && fechas) {
            query.append("AND cliente = ? ");
        } else if(consulta.tieneCampo()){
            query.append("WHERE cliente = ? ");
        }
    }
}
