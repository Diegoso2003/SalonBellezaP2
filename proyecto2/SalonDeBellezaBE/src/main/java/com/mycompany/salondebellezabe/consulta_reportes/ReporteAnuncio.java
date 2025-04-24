/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.consulta_reportes;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.excepciones.NotFoundException;
import com.mycompany.salondebellezabe.modelos.Anuncio;
import com.mycompany.salondebellezabe.repositorio.Repositorio;
import com.mycompany.salondebellezabe.repositorio.anuncios.AnuncioDAO;
import com.mycompany.salondebellezabe.repositorio.anuncios.HistorialAnuncioDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author rafael-cayax
 */
public class ReporteAnuncio extends Repositorio{
    private boolean anuncioMasMostrados = true;
    
    public List<Anuncio> anunciosMasMostrados (Consulta consulta){
        List<Anuncio> anuncios = new ArrayList<>();
        String query2 = armarQuery(consulta);
        obtenerConeccion();
        try (PreparedStatement stmt = coneccion.prepareStatement(query2)){
            colocarFechas(consulta, stmt);
            try(ResultSet result = stmt.executeQuery()){
                while(result.next()){
                    AnuncioDAO repoAnuncio = new AnuncioDAO();
                    Optional<Anuncio> posibleAnuncio = repoAnuncio.obtenerPorID(result.getInt("idAnuncio"));
                    Anuncio anuncio = posibleAnuncio.orElseThrow(() -> new NotFoundException("error al conseguir informacion de los anuncio"));
                    anuncio.setTotalMostrado(result.getInt("total_mostrado"));
                    HistorialAnuncioDAO repoHistorial = new HistorialAnuncioDAO();
                    repoHistorial.setConeccion(coneccion);
                    anuncio.setHistorial(repoHistorial.informacionAnuncio(consulta, anuncio.getIdAnuncio()));
                    anuncios.add(anuncio);
                }
            }
        } catch (SQLException e) {
            throw new InvalidDataException("Error al conseguir el reporte intentar mas tarde");
        } finally {
            cerrar();
        }
        return anuncios;
    }
    
    public List<Anuncio> anunciosMenosMostrados (Consulta consulta){
        this.anuncioMasMostrados = false;
        return anunciosMasMostrados(consulta);
    }

    private void colocarFecha(Consulta consulta, StringBuilder query) {
        if (consulta.tieneAmbas()) {
            query.append("WHERE fechaPublicacion >= ? AND fechaPublicacion <= ? ");
        } else if (consulta.tieneFechaInicio()){
            query.append("WHERE fechaPublicacion >= ? ");
        } else if(consulta.tieneFechaFin()){
            query.append("WHERE fechaPublicacion <= ? ");
        }
    }

    private String armarQuery(Consulta consulta) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT SUM(contador) as total_mostrado, idAnuncio FROM ")
        .append("HistorialAnuncio ");
        colocarFecha(consulta, query);
        query.append("GROUP BY(idAnuncio) ORDER BY(total_mostrado) ");
        String orden = anuncioMasMostrados ? "DESC ": "ASC ";
        query.append(orden);
        query.append("LIMIT 5 ");
        return query.toString();
    }
}
