/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.usuario;

import com.mycompany.salondebellezabe.dtos.CitasEmpleadoDiaDTO;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.repositorio.citas.EmpleadoDAO;
import java.util.List;

/**
 *
 * @author rafael-cayax
 */
public class EmpleadoService {
    private final EmpleadoDAO repositorio;
    
    public EmpleadoService(){
        repositorio = new EmpleadoDAO();
    }
    
    public List<Cita> obtenerCitasDelDia(CitasEmpleadoDiaDTO consulta){
        if (consulta == null || !consulta.esValido()) {
            throw new InvalidDataException("enviar los datos correctamente para la consulta");
        }
        return repositorio.getCitasDelDia(consulta);
    }
}
