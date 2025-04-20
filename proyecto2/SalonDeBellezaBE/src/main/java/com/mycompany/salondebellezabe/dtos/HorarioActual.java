/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.dtos;

import java.time.LocalTime;

/**
 *
 * @author rafael-cayax
 */
public class HorarioActual {
    private LocalTime horaInicioPermitida;
    private LocalTime horaFinPermitida;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public LocalTime getHoraInicioPermitida() {
        return horaInicioPermitida;
    }

    public LocalTime getHoraFinPermitida() {
        return horaFinPermitida;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraInicioPermitida(LocalTime horaInicioPermitida) {
        this.horaInicioPermitida = horaInicioPermitida;
    }

    public void setHoraFinPermitida(LocalTime horaFinPermitida) {
        this.horaFinPermitida = horaFinPermitida;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    
    public boolean estaDentroDelHorarioDelSalon(){
        return esHoraInicioValida() && esHoraFinValida();
    }
    
    public boolean esUnHorarioValidoCita(){
        return esHoraInicioCitaValida() && esHoraFinCitaValida() && sonHorasDiferentes();
    }
    
    private boolean esHoraInicioValida(){
        return estaDentroDelIntervalo(horaInicio) || horaInicio.equals(horaInicioPermitida);
    }
    
    private boolean esHoraFinValida(){
        return estaDentroDelIntervalo(horaFin) || horaFin.equals(horaFinPermitida);
    }
    
    private boolean esHoraInicioCitaValida(){
        return !(estaDentroDelIntervalo(horaInicio));
    }
    
    private boolean esHoraFinCitaValida(){
        return !(estaDentroDelIntervalo(horaFin));
    }
    
    private boolean estaDentroDelIntervalo(LocalTime hora){
        return hora.isAfter(horaInicioPermitida) && hora.isBefore(horaFinPermitida);
    }
    
    private boolean sonHorasDiferentes(){
        return !horaInicio.equals(horaInicioPermitida) && !horaFin.equals(horaFinPermitida);
    }
}
