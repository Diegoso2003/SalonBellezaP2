/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.mycompany.salondebellezabe.excepciones;

/**
 *
 * @author rafael-cayax
 */
public class ConeccionException extends RuntimeException {

    /**
     * Creates a new instance of <code>ConeccionException</code> without detail
     * message.
     */
    public ConeccionException() {
    }

    /**
     * Constructs an instance of <code>ConeccionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ConeccionException(String msg) {
        super(msg);
    }
}
