/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio;

import java.util.Optional;

/**
 *
 * @author rafael-cayax
 * @param <T>
 */
public interface BusquedaPorAtributo<T> {
    public Optional<T> buscarPorAtributo(String nombre);
}
