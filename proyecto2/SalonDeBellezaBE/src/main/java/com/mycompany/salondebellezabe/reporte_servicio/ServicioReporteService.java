/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salondebellezabe.reporte_servicio;

import com.mycompany.salondebellezabe.consulta_reportes.Consulta;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteEmpleadoCitas;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteEmpleadoGanancias;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteServicio;
import com.mycompany.salondebellezabe.consulta_reportes.ReporteServicioGanancias;
import com.mycompany.salondebellezabe.modelos.EmpleadoInforme;
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
    
    /**
     * metodo para obtener los servicios que tengan mas servicio
     * @param consulta los datos de la consulta
     * @return una lista con los datos solicitados
     */
    public List<ReporteServicioCitas> obtenerServicioMasReservado(Consulta consulta){
        return reporte.obtenerServicioMasReservado(consulta);
    }
    
    /**
     * metodo para obtener los servicios que menos citas han hecho
     * @param consulta los datos de la consulta
     * @return una lista con los datos solicitados
     */
    public List<ReporteServicioCitas> obtenerServicioMenosReservado(Consulta consulta){
        return reporte.obtenerServicioMenosReservado(consulta);
    }
    
    /**
     * metodo usado para obtener a los servicios con mas ganancias
     * @param consulta los datos de la consulta
     * @return una lista con los datos
     */
    public List<ServicioGanancias> obtenerGananciasServicio(Consulta consulta){
        ReporteServicioGanancias reporteGanancias = new ReporteServicioGanancias();
        return reporteGanancias.obtenerGananciasServicios(consulta);
    }
    
    /**
     * metodo usado para obtener al servicio con mas ganancias
     * @param consulta los datos para la consulta
     * @return el servicio con mas ganancias
     */
    public ServicioGanancias obtenerServicioMayorGanancia(Consulta consulta){
        return obtenerGananciasServicio(consulta).getFirst();
    }
    
    /**
     * metodo para obtenr las ganancias por empleado
     * @param consulta los datos para la consulta
     * @return una lista con los datos
     */
    public List<EmpleadoInforme> obtenerGananciaEmpleado(Consulta consulta){
        ReporteEmpleadoGanancias reporteEmpleado = new ReporteEmpleadoGanancias();
        return reporteEmpleado.obtenerGananciasEmpleado(consulta);
    }
    
    /**
     * metodo para obtener al empleado que mas citas haya atendido
     * @param consulta los datos para la consulta
     * @return uns lista con los datos solicitados
     */
    public List<EmpleadoInforme> obtenerEmpleadosMasCitas(Consulta consulta){
        ReporteEmpleadoCitas reporteCitas = new ReporteEmpleadoCitas();
        return reporteCitas.obtenerEmpleadoConMasCitas(consulta);
    }
    
}
