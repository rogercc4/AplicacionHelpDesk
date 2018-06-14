/***********************************************************************
 * Module:  ControlReporte.java
 * Author:  Roger
 * Purpose: Defines the Class ControlReporte
 ***********************************************************************/

package helpdesk.web;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import helpdesk.model.*;

/** Permite controlar los reportes que debe mostrar el sistema.
 *
 * @pdOid cf8c9efd-8806-4714-b735-ecd108c06727 */
public class ControlReporte extends javax.servlet.http.HttpServlet {
   /** @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid 6c8376c7-8c37-453c-8762-34498c9463ed */
   private void mostrarRptDetalleSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      // TODO: implement
      String codSolicitud = request.getParameter("codSolic");

      if ( codSolicitud != null ) {

      int codSolic = Integer.parseInt(codSolicitud.trim());

      Solicitud solicitud = Solicitud.getSolicitudBD(codSolic);


            if ( solicitud != null ) {
                try {
                    ArrayList<Tramite> misTramites = solicitud.getTramites();
                    
                    java.util.Map parametros = new java.util.HashMap();
                    parametros.put("TITULO_RPT", "DETALLE DE LA SOLICITUD");

                    Reporte miReporte = new Reporte(parametros, misTramites, "RptDetalleSolicitud.jasper");

                    java.io.InputStream is = null;

                    is = miReporte.getReportePDF();

                    response.setContentType("application/pdf");

                    String nombreFileSolicitud = "detalleSolicitud___" + solicitud.getCodSolicitud() + ".pdf";
                    response.setHeader("Content-disposition", "inline; filename=" + nombreFileSolicitud);
                    
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    OutputStream os = response.getOutputStream();

                        while ((read = is.read(bytes)) != -1) {
                            os.write(bytes, 0, read);
                        }
                    
                    os.flush();
                    os.close();
                } catch (HelpDeskException ex) {
                    Logger.getLogger(ControlReporte.class.getName()).log(Level.SEVERE, null, ex);
                }

            }


       }

      }
   

   /** Called by the server (via the service method) to allow a servlet to handle a GET request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 4ebcf84a-d4da-42bc-9aa6-a131f76ec193 */
    @Override
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      HttpSession miSesion = request.getSession(false);
      String callReporte = request.getParameter("_callReporte");

      if ( callReporte != null && miSesion != null ) {

          if ( callReporte.trim().length() > 0 ) {

              if (callReporte.trim().equals("mostrarRptDetalleSolicitud"))
                  this.mostrarRptDetalleSolicitud(request, response);

          }


      }
      
      


   }

   /** Called by the server (via the service method) to allow a servlet to handle a POST request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid b732184f-d12b-46cd-859e-9dcc00b2a11d */
    @Override
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

   }

}