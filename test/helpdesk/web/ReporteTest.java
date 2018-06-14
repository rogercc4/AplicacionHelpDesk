/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpdesk.web;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import helpdesk.model.data.*;
import helpdesk.model.*;

/**
 *
 * @author Roger
 */
public class ReporteTest {

    public ReporteTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    Conexion miConexion = new Conexion();
    miConexion.setBaseDatos("helpdesk");
    miConexion.setClave("22639443");
    miConexion.setPuerto("5432");
    miConexion.setServidor("localhost");
    miConexion.setUsuario("rcontreras");
    miConexion.setConexion();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }


    /**
     * Test of getReporte method, of class Reporte.
     */
    @Test
    public void testGetReporte() throws HelpDeskException {
        System.out.println("getReporte");

        java.util.ArrayList<Solicitud> miSolic = new java.util.ArrayList<Solicitud>();
        java.util.Map parametros = new java.util.HashMap();
        
        parametros.put("SUB_TITULO_RPT", "ESTADO DE DEUDA - IMPUESTO PREDIAL");
        //miSolic.add(Solicitud.getSolicitudBD(17));

        int codSolicitud1 = 17, codSolicitud2 = 18; 

        java.util.ArrayList<Tramite> misTramites = new java.util.ArrayList<Tramite>();
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud1).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud1).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud1).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud1).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud1).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud1).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud1).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud1).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud2).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud2).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud2).getTramites());
        misTramites.addAll(Solicitud.getSolicitudBD(codSolicitud2).getTramites());

        helpdesk.web.Reporte miReporte = new helpdesk.web.Reporte(parametros, misTramites, "RptDetalleSolicitud.jasper");

        /*byte[] rest = miReporte.getReporte();
        System.out.println(rest.toString()); 
        assertNotNull(rest); */

        miReporte.mostrarReporte();
        
        try {
            Thread.sleep(20000);
            //java.io.InputStream is = miReporte.getReporte();
        } catch (InterruptedException ex) {
            Logger.getLogger(ReporteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

}