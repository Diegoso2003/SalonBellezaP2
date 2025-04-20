/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.validador.cita;

import com.mycompany.salondebellezabe.SumadorDeHoras;
import com.mycompany.salondebellezabe.dtos.CitasEmpleadoDiaDTO;
import com.mycompany.salondebellezabe.dtos.HorarioActual;
import com.mycompany.salondebellezabe.dtos.HorarioEmpleadoDTO;
import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.HorarioSalon;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.repositorio.citas.EmpleadoDAO;
import com.mycompany.salondebellezabe.repositorio.salon_belleza.HorarioSalonRep;
import com.mycompany.salondebellezabe.repositorio.servicios.EmpleadosServicioDAO;
import com.mycompany.salondebellezabe.repositorio.servicios.ServicioDAO;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import com.mycompany.salondebellezabe.validador.Validador;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class ValidadorCita extends Validador<Cita>{

    @Override
    protected boolean esValido() {
        return validarDPIs() && validarFecha() && esHoraValida() && esServicioValido() && esEstadoValido();
    }

    @Override
    public void validarDatos(Cita cita) {
        this.entidad = cita;
        if (!esValido()) {
            throw new InvalidDataException("ingresar los datos necesarios para agendar la cita");
        }
        ValidarEmpleado();
        validarServicio();
        validarHorarioSalon();
        validarHorarioDelEmpleado();
    }

    @Override
    public void validarActualizacion(Cita cita) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private boolean validarDPIs(){
        return this.entidad.getCliente() != null &&this.entidad.getCliente().getDpi() != null
                && this.entidad.getEmpleado() != null&& this.entidad.getEmpleado().getDpi() != null;
    }
    
    private boolean validarFecha(){
        return this.entidad.getFecha() != null;
    }
    
    private boolean esHoraValida(){
        return this.entidad.getHora() != null;
    }
    
    private boolean esServicioValido(){
        return this.entidad.getServicio() != null&&this.entidad.getServicio().getIdServicio() != null;
    }

    private boolean esEstadoValido(){
        return this.entidad.getEstado() != null;
    }
    
    private void ValidarEmpleado() {
        UsuarioDAO repoUsuario = new UsuarioDAO();
        Optional<Usuario> posibleEmpleado = repoUsuario.obtenerPorID(this.entidad.getEmpleado().getDpi());
        Usuario empleado = posibleEmpleado.orElseThrow(() -> new NotFoundException("no se encontro al empleado seleccionado"));
        if (!empleado.isActivo()) {
            throw new NotFoundException("actualmente el empleado no se encuentra disponible para atender este servicio");
        }
        validarAsignacionAlServicio();
    }

    private void validarAsignacionAlServicio() {
        EmpleadosServicioDAO repoEmpleados = new EmpleadosServicioDAO(this.entidad.getServicio());
        Optional<Usuario> posibleEmpleado = repoEmpleados.obtenerPorID(this.entidad.getEmpleado().getDpi());
        if (!posibleEmpleado.isPresent()) {
            throw new InvalidDataException("el empleado seleccionado no se encuentra asignado a este servicio");
        }
    }

    private void validarHorarioDelEmpleado() {
        EmpleadoDAO repoHorario = new EmpleadoDAO();
        CitasEmpleadoDiaDTO horarioDia = new CitasEmpleadoDiaDTO();
        horarioDia.setDpi(this.entidad.getEmpleado().getDpi());
        horarioDia.setFecha(this.entidad.getFecha());
        List<HorarioEmpleadoDTO> horario = repoHorario.obtenerCitasDelEmpleado(horarioDia);
        HorarioActual horarioCita = new HorarioActual();
        horarioCita.setHoraInicio(entidad.getHora());
        horarioCita.setHoraFin(entidad.getHoraFin());
        for(HorarioEmpleadoDTO h : horario){
            horarioCita.setHoraInicioPermitida(h.getHoraInicio());
            horarioCita.setHoraFinPermitida(h.getHoraFin());
            if (!horarioCita.esUnHorarioValidoCita()) {
                throw new InvalidDataException("el horario seleccionado esta reservado");
            }
        }
    }

    private void validarServicio() {
        ServicioDAO repoServicio = new ServicioDAO();
        Optional<Servicio> posibleServicio = repoServicio.obtenerPorID(this.entidad.getServicio().getIdServicio());
        Servicio servicio = posibleServicio.orElseThrow(() -> new NotFoundException("no se encontro el servicio seleccionado"));
        if (!servicio.isActivo()) {
            throw new NotFoundException("el servicio no se encuentra disponible actualmente");
        }
        LocalTime duracion = servicio.getDuracion();
        LocalTime horaInicio = entidad.getHora();
        SumadorDeHoras sumador = new SumadorDeHoras(horaInicio, duracion);
        LocalTime horaFinal = sumador.obtenerSuma();
        entidad.setHoraFin(horaFinal);
    }

    private void validarHorarioSalon() {
        HorarioSalonRep repoHorario = new HorarioSalonRep();
        HorarioSalon horarioSalon = repoHorario.obtenerDatos();
        HorarioActual horario = new HorarioActual();
        horario.setHoraInicioPermitida(horarioSalon.getHoraInicio());
        horario.setHoraFinPermitida(horarioSalon.getHoraFin());
        horario.setHoraInicio(entidad.getHora());
        horario.setHoraFin(entidad.getHoraFin());
        if (!horario.estaDentroDelHorarioDelSalon()) {
            throw new InvalidDataException("el horario seleccionado esta fuera del horario del salon");
        }
    }
    
}
