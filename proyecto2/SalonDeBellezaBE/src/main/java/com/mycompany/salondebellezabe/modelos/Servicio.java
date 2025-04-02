/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.modelos;

import java.time.LocalTime;
import java.util.Set;

/**
 *
 * @author rafael-cayax
 */
public class Servicio {
    private Integer idServicio;
    private String nombreServicio;
    private double precio;
    private LocalTime duracion;
    private String descripcion;
    private ArchivosServicio archivos;
    private boolean activo;
    private Set<Usuario> empleados;

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArchivosServicio getArchivos() {
        return archivos;
    }

    public void setArchivos(ArchivosServicio archivos) {
        this.archivos = archivos;
    }

    public Set<Usuario> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Set<Usuario> empleados) {
        this.empleados = empleados;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
