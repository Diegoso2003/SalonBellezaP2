/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.validador.anuncio;

import com.mycompany.salondebellezabe.excepciones.InvalidDataException;
import com.mycompany.salondebellezabe.modelos.Anuncio;
import com.mycompany.salondebellezabe.modelos.Vigencia;
import com.mycompany.salondebellezabe.modelos.enums.TipoAnuncio;
import com.mycompany.salondebellezabe.validador.Validador;
import com.mycompany.salondebellezabe.validador.usuario.ValidadorFoto;
import java.time.LocalDate;

/**
 *
 * @author rafael-cayax
 */
public class ValidadorAnuncio extends Validador<Anuncio>{
    private static final Integer ANUNCIO_TEXTO_MAS_CORTO = 10;
    private static final Integer ANUNCIO_TEXTO_MAS_LARGO = 150;
    private static final Integer URL_VIDEO_MAS_LARGO = 250;
    private static final Integer URL_VIDEO_MAS_CORTO = 10;

    @Override
    protected boolean esValido() {
        TipoAnuncio tipo = entidad.getTipo();
        if (tipo == null) {
            return false;
        }
        switch(tipo){
            case IMAGEN:
                return esAnuncioDeImagenValido();
            case TEXTO:
                return esAnuncioDeTextoValido();
            case VIDEO:
                return esAnuncioDeVideoValido();
            default:
                return false;
        }
    }

    @Override
    public void validarDatos(Anuncio anuncio) {
        this.entidad = anuncio;
        if (!esValido()) {
            throw new InvalidDataException("ingresar correctamente los datos solicitados");
        }
    }

    private boolean esAnuncioDeImagenValido() {
        ValidadorFoto validadorFoto = new ValidadorFoto();
        validadorFoto.validarDatos(entidad.getFoto());
        return esAnuncioDeTextoValido();
    }

    private boolean esAnuncioDeTextoValido() {
        return esTextoValido() && esVigenciaValida();
    }

    private boolean esAnuncioDeVideoValido() {
        return esAnuncioDeTextoValido() && esUrlVideoValido();
    }
    
    private boolean esTextoValido(){
        String texto = entidad.getTexto();
        return cumpleRangoNormal(texto, ANUNCIO_TEXTO_MAS_CORTO, ANUNCIO_TEXTO_MAS_LARGO);
    }

    private boolean esVigenciaValida() {
        Vigencia vigencia = entidad.getVigencia();
        if(vigencia == null){
            return false;
        }
        return esFechaPublicacionValida() && sonDiasValidos();
    }

    private boolean esFechaPublicacionValida() {
        LocalDate fecha = entidad.getVigencia().getFechaPublicacion();
        return fecha != null;
    }

    private boolean sonDiasValidos() {
        Integer dias = entidad.getVigencia().getDias();
        return dias != null && dias > 0;
    }

    private boolean esUrlVideoValido() {
        String url = entidad.getUrlVideo();
        return cumpleRangoSinReemplazo(url, URL_VIDEO_MAS_CORTO, URL_VIDEO_MAS_LARGO);
    }

    @Override
    public void validarActualizacion(Anuncio entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
