/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe;

import java.time.LocalTime;

/**
 *
 * @author rafael-cayax
 */
public class SumadorDeHoras {
    private final LocalTime horaInicial;
    private final LocalTime horaASumar;

    public SumadorDeHoras(LocalTime horaInicial, LocalTime horaASumar) {
        this.horaInicial = horaInicial;
        this.horaASumar = horaASumar;
    }
    
    public LocalTime obtenerSuma(){
        int hora = horaASumar.getHour();
        int minuto = horaASumar.getMinute();
        LocalTime horaFinal = 
        horaInicial.plusHours(hora).plusMinutes(minuto);
        return horaFinal;
    }
    
}
