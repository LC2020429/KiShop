/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luiscordova.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.luiscordova.dao.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametros){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport ReporteClientes2 =(JasperReport) JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(ReporteClientes2, parametros, Conexion.getInstance().getConexion());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
/**
 * Ibnterfaces Map
 *  hasmap ES  UNO  de los onjetos que implementamos en un conjunto de Key-value
 * tiene un costrunctor sin parametros new HasMap() y su finalidad seuel referirs para
 * agrupar informacion en un unico objeto
 * 
 * Tiene cierta similtud con la coleccion de objetos (ArrayList) pero con la 
 * diferecia  de que no tienen orden
 * 
 * Hash hace referencia a un tecnica de organizacoin de archivos, hashing (abierto-cerrado )
 * en la que se almacena registro en una direccion aplicando que es generada por una funcion que se aplica a ala llave del 
 * que le indiquemos 
 * 
 * En java el HasmAP PASEE UN ESPACIO de memoria y cuando se guarda un objeto en dicho espacio
 * se determina su direccion 
 */
