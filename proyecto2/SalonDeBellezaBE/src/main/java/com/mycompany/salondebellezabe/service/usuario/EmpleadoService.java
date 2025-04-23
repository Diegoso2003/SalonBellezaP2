/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.usuario;

import com.mycompany.salondebellezabe.dtos.CitasEmpleadoDiaDTO;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.enums.EstadoCita;
import com.mycompany.salondebellezabe.repositorio.citas.CitaDAO;
import com.mycompany.salondebellezabe.repositorio.citas.EmpleadoDAO;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class EmpleadoService {
    private final EmpleadoDAO repositorio;
    private CitaDAO repositorioCita;
    
    public EmpleadoService(){
        repositorio = new EmpleadoDAO();
    }
    
    public List<Cita> obtenerCitasDelDia(CitasEmpleadoDiaDTO consulta){
        if (consulta == null || !consulta.esValido()) {
            throw new InvalidDataException("enviar los datos correctamente para la consulta");
        }
        return repositorio.getCitasDelDia(consulta);
    }

    public void marcarCitaComoAtendida(Integer idCita) {
        repositorioCita = new CitaDAO();
        Cita cita = validarCita(idCita);
        repositorioCita.citaAtendida(cita);
    }

    public void marcarCitaComoAusente(Integer idCita) {
        repositorioCita = new CitaDAO();
        Cita cita = validarCita(idCita);
        repositorioCita.citaAusente(cita);
    }

    private Cita validarCita(Integer idCita) {
        Optional<Cita> posibleCita = repositorioCita.obtenerPorID(idCita);
        Cita cita = posibleCita.orElseThrow(() -> new NotFoundException("cita con id: '" + idCita + "' no encontrada"));
        if (cita.getEstado() != EstadoCita.PROGRAMADA) {
            throw new InvalidDataException("No es posible cambiar el estado de la cita con id: '" + idCita + "'");
        }
        return cita;
    }
}
