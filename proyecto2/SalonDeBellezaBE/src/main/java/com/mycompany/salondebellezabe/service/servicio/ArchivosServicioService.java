/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service.servicio;

import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.ArchivosServicio;
import com.mycompany.salondebellezabe.repositorio.servicios.ArchivosServicioDAO;
import com.mycompany.salondebellezabe.service.Service;
import com.mycompany.salondebellezabe.validador.servicio.ValidadorServicio;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class ArchivosServicioService extends Service<ArchivosServicio>{
    private final ArchivosServicioDAO repositorioArchivos;
    private final ValidadorServicio validadorServicio;

    public ArchivosServicioService() {
        super(new ArchivosServicioDAO(), new ValidadorServicio(), "error al cargar los archivos");
        this.repositorioArchivos = (ArchivosServicioDAO) repositorio;
        this.validadorServicio = (ValidadorServicio) validador;
    }

    public ArchivosServicio encontrarCatalogo(String idServicioEnviado) {
        Integer idServicio = validador.validarId(idServicioEnviado);
        Optional<ArchivosServicio> posibleCatalogo = repositorioArchivos.obtenerCatalgo(idServicio);
        return posibleCatalogo.orElseThrow(() -> new NotFoundException("catalogo no encontrado"));
    }
    
    
    
}
