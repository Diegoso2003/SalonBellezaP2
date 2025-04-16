/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.salondebellezabe.validador;

/**
 *
 * @author rafael-cayax
 */
public abstract class Validador<T> {
    protected T entidad;
    
    protected abstract boolean esValido();
    public abstract void validarDatos(T entidad);
    public abstract void validarActualizacion(T entidad);
    
    /**
     * metodo para evaluar si una cadena tiene el minimo aceptado
     * @param cadena la cadena a analizar
     * @param minimo el minimo
     * @return true si cumple el minimo false cualquier otra cosa
     */
    protected boolean esLargoMinimo(String cadena, Integer minimo){
        if (cadena == null) {
            return false;
        }
        return !cadena.isBlank() && cadena.length() >= minimo;
    }
    
    /**
     * metodo para evaluar si la cadena esta dentro del largo permitido usa trim 
     * y replaceAll
     * @param cadena la cadena a analizar
     * @param minimo el minimo 
     * @param maximo el maximo
     * @return true si esta dentro del rengo false para todo lo demas
     */
    protected boolean cumpleRangoConReemplazo(String cadena, Integer minimo, Integer maximo){
        if (cadena == null) {
            return false;
        }
        cadena = cadena.trim().replaceAll("\\s+", " ");
        return cadena.length() >= minimo && cadena.length() <= maximo;
    }
    
    /**
     * metodo para evaluar si una cadena esta dentro de un rango solo usa trim
     * @param cadena la cadena a evaluar
     * @param minimo el minimo
     * @param maximo el maximo
     * @return true si esta dentro del rengo, false para todo lo demas
     */
    protected boolean cumpleRangoSinReemplazo(String cadena, Integer minimo, Integer maximo){
        if (cadena == null) {
            return false;
        }
        cadena = cadena.trim();
        return cadena.length() >= minimo && cadena.length() <= maximo;
    }
    
    /**
     * metodo para saber si una cadena esta dentro del rango de una longitud
     * @param cadena la cadena a evaluar
     * @param minimo el minimo
     * @param maximo el maximo
     * @return true si esta dentro del rango, false para todo lo demas
     */
    protected boolean cumpleRangoNormal(String cadena, Integer minimo, Integer maximo){
        return cadena != null && cadena.length() >= minimo && cadena.length() <= maximo && !cadena.isBlank();
    }
}
