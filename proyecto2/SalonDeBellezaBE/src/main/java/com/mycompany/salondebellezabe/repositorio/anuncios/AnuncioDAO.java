/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.repositorio.anuncios;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Anuncio;
import com.mycompany.salondebellezabe.modelos.enums.TipoAnuncio;
import com.mycompany.salondebellezabe.repositorio.ClaseDAO;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class AnuncioDAO extends ClaseDAO<Anuncio, Integer>{
    
    /**
     * metodo usado para insertar publicar un nuevo anuncio
     * @param anuncio los datos del anuncio
     */
    @Override
    public void insertar(Anuncio anuncio) {
        obtenerConeccion();
        boolean urlVideo = anuncio.getTipo() == TipoAnuncio.VIDEO;
        String query = "INSERT INTO Anuncio(tipo, texto";
        query += urlVideo ? ", urlVideo)": ")";
        query += " VALUES(?, ?";
        query += urlVideo ? ", ?)": ")";
        try (PreparedStatement stmt = coneccion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            coneccion.setAutoCommit(false);
            stmt.setString(1, anuncio.getTipo().toString());
            stmt.setString(2, anuncio.getTexto());
            if (urlVideo) {
                stmt.setString(3, anuncio.getUrlVideo().trim());
            }
            if (stmt.executeUpdate() > 0) {
                try (ResultSet result = stmt.getGeneratedKeys()) {
                    if (result.next()) {
                        idGenerado = result.getInt(1);
                        anuncio.getVigencia().setIdAnuncio(idGenerado);
                        VigenciaDAO repoVigencia = new VigenciaDAO();
                        repoVigencia.setConeccion(coneccion);
                        repoVigencia.insertar(anuncio.getVigencia());
                        if (anuncio.getTipo() == TipoAnuncio.IMAGEN) {
                            ImagenAnuncioDAO repoImagen = new ImagenAnuncioDAO(idGenerado);
                            repoImagen.setConeccion(coneccion);
                            repoImagen.insertar(anuncio.getFoto());
                        }
                        coneccion.commit();
                    }
                }
            }
        } catch (SQLException e) {
            regresar();
            throw new InvalidDataException("ingrese correctamente los datos solicitados");
        } finally {
            cerrar();
        }
    }

    /**
     * metodo usado para hacer que un anuncio sea desactivado
     * @param id el id del anuncio a desactivar
     */
    @Override
    public void eliminar(Integer id) {
        obtenerConeccion();
        String query = "UPDATE Anuncio SET estado = FALSE WHERE idAnuncio = ?";
        try (PreparedStatement stmt = coneccion.prepareStatement(query)){
            stmt.setInt(1, id);
            if (stmt.executeUpdate() <= 0) {
                throw new NotFoundException("no se encontro el anuncio con id: '" + id + "'");
            }
        } catch (SQLException e) {
            throw new InvalidDataException("Error al desactivar el anuncio");
        } finally {
            cerrar();
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
        obtenerConeccion();
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
        } finally {
            cerrar();
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
        VigenciaDAO repoVigencia = new VigenciaDAO();
        repoVigencia.setConeccion(coneccion);
        anuncio.setVigencia(repoVigencia.obtenerVigenciaActualAnuncio(anuncio.getIdAnuncio()));
        return anuncio;
    }
    
    /**
     * metodo para obtener todos los anuncios activos
     * @return lista de anuncios activos
     */
    public List<Anuncio> obtenerAnunciosActivos(){
        return listarPorAtributos("SELECT * FROM Anuncio WHERE estado = TRUE");
    }
    
}
