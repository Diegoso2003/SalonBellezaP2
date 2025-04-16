/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.service;

import com.mycompany.salondebellezabe.repositorio.TablasUnicas;
import com.mycompany.salondebellezabe.validador.Validador;

/**
 *
 * @author rafael-cayax
 */
public class ServicioSalon<T> {
    private final TablasUnicas repositorio;
    private final Validador validador;

    public ServicioSalon(TablasUnicas repositorio, Validador validador) {
        this.repositorio = repositorio;
        this.validador = validador;
    }
    
    public T obtenerDatos(){
        return (T)repositorio.obtenerDatos();
    }
    
    public void actualizar(T entidad){
        validador.validarActualizacion(entidad);
        repositorio.actualizarTabla(entidad);
    }
}
