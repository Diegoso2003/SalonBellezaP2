/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.reporte_servicio;

import com.mycompany.salondebellezabe.consulta_reportes.Consulta;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteServicio;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteServicioGanancias;
import com.mycompany.salondebellezabe.modelos.ReporteServicioCitas;
import com.mycompany.salondebellezabe.modelos.ServicioGanancias;
import java.util.List;

/**
 *
 * @author rafael-cayax
 */
public class ServicioReporteService {
    private final ReporteServicio reporte;

    public ServicioReporteService() {
        reporte = new ReporteServicio();
    }
    
    public List<ReporteServicioCitas> obtenerServicioMasReservado(Consulta consulta){
        return reporte.obtenerServicioMasReservado(consulta);
    }
    
    public List<ReporteServicioCitas> obtenerServicioMenosReservado(Consulta consulta){
        return reporte.obtenerServicioMenosReservado(consulta);
    }
    
    public List<ServicioGanancias> obtenerGananciasServicio(Consulta consulta){
        ReporteServicioGanancias reporte = new ReporteServicioGanancias();
        return reporte.obtenerGananciasServicios(consulta);
    }
    
}
