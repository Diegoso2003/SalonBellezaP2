/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

/**
 *
 * @author rafael-cayax
 */
public class HorarioSalon {
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInicio;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFin;

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    
    public boolean esHorarioValido(){
        return horaInicio != null && horaFin != null
                && horaInicio.isBefore(horaFin); 
    }
}
