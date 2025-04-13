/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.servicio;

import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Servicio;
import com.mycompany.salondebellezabe.modelos.Usuario;
import com.mycompany.salondebellezabe.repositorio.servicios.ServicioDAO;
import com.mycompany.salondebellezabe.repositorio.usuarios.UsuarioDAO;
import com.mycompany.salondebellezabe.service.Service;
import com.mycompany.salondebellezabe.validador.servicio.ValidadorServicio;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class ServicioService extends Service<Servicio>{
    private final ServicioDAO repositorioServicio;
    private final ValidadorServicio validadorServicio;
    
    public ServicioService() {
        super(new ServicioDAO(), new ValidadorServicio());
        this.repositorioServicio = (ServicioDAO)repositorio;
        this.validadorServicio = (ValidadorServicio) validador;
    }
    
    /**
     * metodo usado para obtener los empleados que esten activos
     * @return los empleados activos
     */
    public List<Usuario> obtenerEmpleados(){
        UsuarioDAO repositorioUsuario = new UsuarioDAO();
        return repositorioUsuario.obtenerEmpleados();
    }
    
    /**
     * metodo usado para obtener la descripcion de un empleado
     * @param dpi el dpi del empleado
     * @return el usuario con su descripcion y detalles
     */
    public Usuario obtenerInformacionEmpleado(Long dpi){
        UsuarioDAO repositorioUsuario = new UsuarioDAO();
        repositorioUsuario.setObtenerDatos(true);
        Optional<Usuario> posibleUsuario = repositorioUsuario.obtenerPorID(dpi);
        return posibleUsuario.orElseThrow(() -> new NotFoundException("empleado no encontrado"));
    }
    
}
