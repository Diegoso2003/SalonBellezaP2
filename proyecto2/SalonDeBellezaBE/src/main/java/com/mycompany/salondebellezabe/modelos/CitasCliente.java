/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.modelos;

import java.util.List;

/**
 *
 * @author rafael-cayax
 */
public class CitasCliente {
    private Usuario cliente;
    private List<Cita> citas;
    private Integer totalCitas;

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public Integer getTotalCitas() {
        return totalCitas;
    }

    public void setTotalCitas(Integer totalCitas) {
        this.totalCitas = totalCitas;
    }
    
}
