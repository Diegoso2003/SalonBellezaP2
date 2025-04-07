/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author rafael-cayax
 */
public class Encriptador {
    /**
     * metodo para evaluar la contraseña ingresada con la que esta encriptada
     * @param entrada la contraseña ingresada
     * @param hasheada la contraseña guardada en la base de datos
     * @return 
     */
    public boolean esValida(String entrada, String hasheada){
        return BCrypt.checkpw(entrada, hasheada);
    }
    
    /**
     * metodo para encriptar la contraseña ingresada
     * @param contraseña la contraseña a encriptar
     * @return la contraseña encriptada
     */
    public String encriptar(String contraseña){
        return BCrypt.hashpw(contraseña, BCrypt.gensalt());
    }
}
