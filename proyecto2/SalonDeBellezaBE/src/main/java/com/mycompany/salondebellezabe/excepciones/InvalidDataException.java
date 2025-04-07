/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.mycompany.salondebellezabe.excepciones;

/**
 *
 * @author rafael-cayax
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvalidDataException</code> without
     * detail message.
     */
    public InvalidDataException() {
    }

    /**
     * Constructs an instance of <code>InvalidDataException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidDataException(String msg) {
        super(msg);
    }
}
