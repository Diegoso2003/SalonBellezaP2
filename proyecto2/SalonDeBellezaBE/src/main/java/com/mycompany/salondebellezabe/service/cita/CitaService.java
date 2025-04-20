/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.cita;

import com.mycompany.salondebellezabe.dtos.CitaDTO;
import com.mycompany.salondebellezabe.dtos.MensajeDTO;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Cita;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.modelos.enums.EstadoCita;
import com.mycompany.salondebellezabe.repositorio.citas.CitaDAO;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import com.mycompany.salondebellezabe.service.Service;
import com.mycompany.salondebellezabe.validador.cita.ValidadorCita;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class CitaService extends Service<Cita>{
    
    public CitaService() {
        super(new CitaDAO(), new ValidadorCita(), "Cita no encontrada");
    }

    public MensajeDTO agendarCita(CitaDTO informacionCita) {
        MensajeDTO mensaje = new MensajeDTO();
        Cita cita = obtenerInformacionCita(informacionCita);
        obtenerInformacionCliente(cita);
        this.crearEntidad(cita);
        boolean programada = cita.getEstado() == EstadoCita.PROGRAMADA;
        String resultado = programada ? "Cita programada a la hora indicada": "Cita pendiente de aceptar por el administrado";
        mensaje.setMensaje(resultado);
        return mensaje;
    }

    private Cita obtenerInformacionCita(CitaDTO informacionCita) {
        Cita cita = new Cita();
        Usuario cliente = new Usuario();
        cliente.setDpi(informacionCita.getDpiCliente());
        cita.setCliente(cliente);
        Usuario empleado = new Usuario();
        empleado.setDpi(informacionCita.getDpiEmpleado());
        cita.setEmpleado(empleado);
        cita.setHora(informacionCita.getHora());
        cita.setFecha(informacionCita.getFecha());
        Servicio servicio = new Servicio();
        servicio.setIdServicio(informacionCita.getIdServicio());
        cita.setServicio(servicio);
        return cita;
    }

    private void obtenerInformacionCliente(Cita cita) {
        UsuarioDAO repoUsuario = new UsuarioDAO();
        Optional<Usuario> posibleCliente = repoUsuario.obtenerPorID(cita.getCliente().getDpi());
        Usuario cliente = posibleCliente.orElseThrow(() -> new NotFoundException("no se pudo encontrar los datos de la sesion actual"));
        if (cliente.isListaNegra()) {
            cita.setEstado(EstadoCita.PENDIENTE);
        } else {
            cita.setEstado(EstadoCita.PROGRAMADA);
        }
    }

}
