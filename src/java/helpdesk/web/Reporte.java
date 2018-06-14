/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpdesk.web;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JasperPrint ;
import net.sf.jasperreports.engine.JasperFillManager ;
import net.sf.jasperreports.view.JasperViewer ;
import net.sf.jasperreports.engine.JRException;
import java.util.Map;
import net.sf.jasperreports.engine.JasperPrintManager;

import java.io.*;
import net.sf.jasperreports.engine.JasperRunManager;

/**
Representa a un reporte del sistema

@author Roger A. Contreras Corrales
 */
public class Reporte {

   /**
   Contiene los parametros del reporte y sus valores
    */
private Map parametros;

   /**
   Datos que se envian para ser visualizados
    */
private List datos;


private JRBeanCollectionDataSource dataSource;

   /**
   Ruta del archivo de reporte compilado (Archivo con extension jasper)
    */
private InputStream archivoReporte;


    public Reporte( Map misParametros, List misDatos, String miReporte ) throws helpdesk.model.HelpDeskException {

            this.parametros = misParametros;
            this.datos = misDatos;

            if (this.datos.size() <= 0) {
                throw new helpdesk.model.HelpDeskException("No hay datos que imprimir");
            }

            if ( miReporte.trim().length() <= 0 ) {
                throw new helpdesk.model.HelpDeskException("Falta especificar el nombre del reporte");
            }

            this.archivoReporte = this.getClass().getResourceAsStream("/reportes/" + miReporte.trim());

            this.dataSource = new JRBeanCollectionDataSource(this.datos);

    }

    /**
   Permite obtener una vista del reporte antes de ser impresa
    */
    public void mostrarReporte() {

            try {

                JasperPrint jasperPrint = JasperFillManager.fillReport(this.archivoReporte, this.parametros, this.dataSource);
                
                JasperViewer jviewer = new JasperViewer(jasperPrint, false);
                jviewer.setZoomRatio(0.80f);
                jviewer.setVisible(true);
                

            }
            catch (JRException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());

            }
      

    }

    /**
   Imprime directamente el reporte en la impresora
    */
    public void imprimir() {

            try {

                JasperPrint jasperPrint = JasperFillManager.fillReport(this.archivoReporte, this.parametros, this.dataSource);
            
                JasperPrintManager.printReport(jasperPrint, false);

            }
            catch (JRException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
            }

    }


    public java.io.InputStream getReportePDF() {
    java.io.ByteArrayInputStream fis = null;
    byte[]  fichero = null; 
    
        try {
            fichero = JasperRunManager.runReportToPdf(this.archivoReporte, this.parametros, this.dataSource);
            
            fis = new java.io.ByteArrayInputStream(fichero);

        } catch (JRException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return fis;
     
    }


}