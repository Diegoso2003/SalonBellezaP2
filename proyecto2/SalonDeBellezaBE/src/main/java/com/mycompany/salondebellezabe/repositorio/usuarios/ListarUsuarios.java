/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.usuarios;

import com.mycompany.salondebellezabe.modelos.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael-cayax
 */
public class ListarUsuarios extends UsuarioDAO{
    
    public ListarUsuarios(Connection coneccion) {
        super(coneccion);
    }
    
    public List<Usuario> obtenerClientesListaNegra(){
        return listarPorAtributos("SELECT * FROM Usuario WHERE rol = 'CLIENTE' AND listaNegra = TRUE");
        
    }
    
    public List<Usuario> obtenerPersonal(){
        return listarPorAtributos("SELECT * FROM Usuario WHERE rol != 'CLIENTE'");
    }
    
}
