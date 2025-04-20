/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.citas;

import com.mycompany.salondebellezabe.SumadorDeHoras;
import com.mycompany.salondebellezabe.dtos.CitasEmpleadoDiaDTO;
import com.mycompany.salondebellezabe.dtos.HorarioEmpleadoDTO;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael-cayax
 */
public class EmpleadoDAO extends Repositorio{
    
    
    public List<HorarioEmpleadoDTO> obtenerCitasDelEmpleado(CitasEmpleadoDiaDTO consulta){
        List<HorarioEmpleadoDTO> horario = new ArrayList<>();
        obtenerConeccion();
        String query = "SELECT duracion, hora FROM Cita c INNER JOIN Servicio s ON s.idServicio = c.idServicio"
                + " WHERE empleado = ? AND fecha = ? AND estado = 'PROGRAMADA' ORDER BY(hora) ASC";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setLong(1, consulta.getDpi());
            stmt.setDate(2, Date.valueOf(consulta.getFecha()));
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    LocalTime duracion = result.getTime("duracion").toLocalTime();
                    LocalTime hora = result.getTime("hora").toLocalTime();
                    HorarioEmpleadoDTO resultado = obtenerDuracion(duracion, hora);
                    horario.add(resultado);
                }
            }
        } catch (SQLException e) {
            throw new InvalidDataException("Error al conseguir el horario del empleado en la fecha indicada");
        } finally {
            cerrar();
        }
        return horario;
    }

    private HorarioEmpleadoDTO obtenerDuracion(LocalTime duracion, LocalTime hora) {
        HorarioEmpleadoDTO horario = new HorarioEmpleadoDTO();
        SumadorDeHoras sumador = new SumadorDeHoras(hora, duracion);
        horario.setHoraInicio(hora);
        horario.setHoraFin(sumador.obtenerSuma());
        return horario;
    }

}
