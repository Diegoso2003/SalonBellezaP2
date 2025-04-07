/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.mycompany.salondebellezabe.excepciones;

/**
 *
 * @author rafael-cayax
 */
public class NoAutorizadoException extends RuntimeException {

    /**
     * Creates a new instance of <code>NoAutorizadoException</code> without
     * detail message.
     */
    public NoAutorizadoException() {
    }

    /**
     * Constructs an instance of <code>NoAutorizadoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoAutorizadoException(String msg) {
        super(msg);
    }
}
