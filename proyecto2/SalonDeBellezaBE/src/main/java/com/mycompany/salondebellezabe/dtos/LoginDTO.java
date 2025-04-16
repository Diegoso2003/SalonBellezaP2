/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.dtos;

import com.mycompany.salondebellezabe.modelos.enums.Rol;

/**
 *
 * @author rafael-cayax
 */
public class LoginDTO {
    private Long dpi;
    private boolean activo;
    private boolean listaNegra;
    private Rol rol;

    /**
     * constructor de informacion para guardar la informacion del inicio de sesion
     * @param dpi el dpi del usuario
     * @param activo si esta activo
     * @param listaNegra si esta en la lista negra
     */
    public LoginDTO(Long dpi, boolean activo, boolean listaNegra, Rol rol) {
        this.dpi = dpi;
        this.activo = activo;
        this.listaNegra = listaNegra;
        this.rol = rol;
    }

    public Long getDpi() {
        return dpi;
    }

    public void setDpi(Long dpi) {
        this.dpi = dpi;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isListaNegra() {
        return listaNegra;
    }

    public void setListaNegra(boolean listaNegra) {
        this.listaNegra = listaNegra;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
}
