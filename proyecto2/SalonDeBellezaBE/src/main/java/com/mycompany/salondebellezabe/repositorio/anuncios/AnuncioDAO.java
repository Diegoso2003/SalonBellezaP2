/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.anuncios;

import com.mycompany.salondebellezabe.modelos.Anuncio;
import com.mycompany.salondebellezabe.modelos.enums.TipoAnuncio;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class AnuncioDAO extends Repositorio<Anuncio, Integer>{

    /**
     * metodo usado para insertar publicar un nuevo anuncio
     * @param anuncio los datos del anuncio
     */
    @Override
    public void insertar(Anuncio anuncio) {
        String query = "INSERT INTO Anuncio(tipo, texto, urlVideo) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = coneccion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, anuncio.getTipo().toString());
            stmt.setString(2, anuncio.getTexto());
            stmt.setString(3, anuncio.getUrlVideo());
            if (stmt.executeUpdate() > 0) {
                try (ResultSet result = stmt.getGeneratedKeys()) {
                    idGenerado = result.getInt(1);
                }
            }
        } catch (SQLException e) {
            //datos ingresados no validos
        }
    }

    /**
     * metodo usado para hacer que un anuncio sea desactivado
     * @param id el id del anuncio a desactivar
     */
    @Override
    public void eliminar(Integer id) {
        String query = "UPDATE Anuncio SET estado = FALSE WHERE idAnuncio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, id);
            if (stmt.executeUpdate() <= 0) {
                //no se encontro el anuncio
            }
        } catch (SQLException e) {
            //hubo un error
        }
    }

    /**
     * metodo para obtener los datos de un anuncio buscandolo por su id
     * @param id el id del anuncio
     * @return un optional con el anuncio si es encontrado
     */
    @Override
    public Optional<Anuncio> obtenerPorID(Integer id) {
        String query = "SELECT * FROM Anuncio WHERE idAnuncio = ?";
        return buscar(query, id, JDBCType.INTEGER);
    }

    @Override
    public void actualizar(Anuncio anuncio) {
        String query = "UPDATE Anuncio SET texto = ?, urlVideo = ? WHERE idAnuncio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setString(1, anuncio.getTexto());
            stmt.setString(2, anuncio.getUrlVideo());
            stmt.setInt(3, anuncio.getIdAnuncio());
            if (stmt.executeUpdate() <= 0) {
                //anuncio no encotrado
            }
        } catch (SQLException e) {
            //datos ingresados invalidos
        }
    }

    /**
     * obtiene todos los anunios
     * @return lista con todos los anuncios
     */
    @Override
    public List<Anuncio> obtenerTodo() {
        return listarPorAtributos("SELECT * FROM Anuncio");
    }
    
    /**
     * metodo usado para obtener los datos de un anuncio a partir del result
     * @param result los datos de la consulta
     * @return anuncio con sus datos
     * @throws SQLException 
     */
    @Override
    protected Anuncio obtenerDatos(ResultSet result) throws SQLException {
        Anuncio anuncio = new Anuncio();
        anuncio.setIdAnuncio(result.getInt("idAnuncio"));
        anuncio.setTexto(result.getString("texto"));
        anuncio.setUrlVideo(result.getString("urlVideo"));
        anuncio.setEstado(result.getBoolean("estado"));
        anuncio.setTipo(TipoAnuncio.valueOf(result.getString("tipo")));
        return anuncio;
    }
    
    /**
     * metodo que sirve para obtener los ids de los anuncios que todavia tengan
     * vigencia
     * @return lista con los id de anuncios vigentes
     */
    public List<Integer> obtenerIDactivos(){
        List<Integer> idsActivos = new ArrayList<>();
        String actu = "UPDATE Anuncio SET estado = FALSE WHERE idAnuncio IN "
                + "(SELECT DISTINCT a.idAnuncio FROM Anuncio a "
                + "INNER JOIN Vigencia v ON a.idAnuncio = v.idAnuncio "
                + "WHERE (DATEDIFF(CURDATE(), v.fechaPublicacion)) > v.dias";
        String query = "SELECT idAnuncio FROM Anuncio WHERE estado = TRUE";
        try (Statement stmt = coneccion.createStatement();
                Statement stmt2 = coneccion.createStatement()){
            stmt.executeUpdate(actu);
            try(ResultSet result = stmt2.executeQuery(query)){
                while(result.next()){
                    idsActivos.add(result.getInt("idAnuncio"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();//algo salio mal
        }
        return idsActivos;
    }
}
