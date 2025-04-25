/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.reporte_cliente;

import com.mycompany.salondebellezabe.consulta_reportes.Consulta;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteClienteCitas;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteClienteGastos;
import com.mycompany.salondebellezabe.modelos.CitasCliente;
import com.mycompany.salondebellezabe.modelos.GastosCliente;
import java.util.List;

/**
 *
 * @author rafael-cayax
 */
public class ClienteReporteService {
    private final ReporteClienteGastos repositorio;
    
    public ClienteReporteService() {
        repositorio = new ReporteClienteGastos();
    }
    
    public List<GastosCliente> obtenerUsuarioMasGasta(Consulta consulta){
        return repositorio.obtenerClienteMasGasto(consulta);
    }
    
    public List<GastosCliente> obtenerUsuarioMenosGasto(Consulta consulta){
        return repositorio.obtenerClienteMenosGasto(consulta);
    }
    
    public List<CitasCliente> obtenerUsuarioMasReservaciones(Consulta consulta){
        ReporteClienteCitas reporte = new ReporteClienteCitas();
        return reporte.clienteConMasCitas(consulta);
    }
    
    public List<CitasCliente> obtenerUsuarioMenosResevacionse(Consulta consulta){
        ReporteClienteCitas reporte = new ReporteClienteCitas();
        return reporte.clienteConMenosCitas(consulta);
    }
}
