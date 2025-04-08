/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.salon_belleza;

import com.mycompany.salondebellezabe.Coneccion;
import com.mycompany.salondebellezabe.modelos.HorarioSalon;
import com.mycompany.salondebellezabe.repositorio.TablasUnicas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 *
 * @author rafael-cayax
 */
public class HorarioSalonRep extends TablasUnicas<HorarioSalon> {

    public HorarioSalonRep() {
        super("HorarioSalon");
    }

    /**
     * metodo para actualizar el horario del salon
     *
     * @param horario el nuevo horario
     */
    @Override
    public void actualizarTabla(HorarioSalon horario) {
        String query = "UPDATE HorarioSalon SET horaInicio = ?, horaFin = ?";
        try (Connection coneccion = Coneccion.getConeccion();
                PreparedStatement stmt = coneccion.prepareStatement(query)) {
            stmt.setTime(1, Time.valueOf(horario.getHoraInicio()));
            stmt.setTime(2, Time.valueOf(horario.getHoraFin()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            //valores ingresados invalidos
        }
    }

    /**
     * metodo para obtener el horario del salon
     *
     * @param resultadoConsulta el resultado de la consulta
     * @return el horario actual del salon
     * @throws SQLException
     */
    @Override
    protected HorarioSalon obtenerDatos(ResultSet resultadoConsulta) throws SQLException {
        HorarioSalon horario = new HorarioSalon();
        horario.setHoraInicio(resultadoConsulta.getTime("horaInicio").toLocalTime());
        horario.setHoraFin(resultadoConsulta.getTime("horaFint").toLocalTime());
        return horario;
    }

}
