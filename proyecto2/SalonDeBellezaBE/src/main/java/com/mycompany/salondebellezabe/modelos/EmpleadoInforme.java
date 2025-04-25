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
public class EmpleadoInforme {
    private Integer totalCitas;
    private Double totalGanancias;
    private Usuario empleado;
    private List<Cita> citas;

    public Integer getTotalCitas() {
        return totalCitas;
    }

    public void setTotalCitas(Integer totalCitas) {
        this.totalCitas = totalCitas;
    }

    public Double getTotalGanancias() {
        return totalGanancias;
    }

    public void setTotalGanancias(Double totalGananacias) {
        this.totalGanancias = totalGananacias;
    }

    public Usuario getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
    
}
