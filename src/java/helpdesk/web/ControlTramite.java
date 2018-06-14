/***********************************************************************
 * Module:  ControlTramite.java
 * Author:  rcontreras
 * Purpose: Defines the Class ControlTramite
 ***********************************************************************/

package helpdesk.web;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import helpdesk.model.* ; 

/** Sirve para controlar las operaciones de la clase Tramite. La clase Tramite es la que sirve para representar los tramites que son generados durante el proceso
 * de atencion de una solicitud
 *
 * @pdOid 02c184ce-905c-457b-bb4b-ec37738dd207 */
public class ControlTramite extends javax.servlet.http.HttpServlet {
   /** Permite mostrar el detalle del tramite por el que pasa una solicitud
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 6c35a21a-b17d-4a0f-9979-17de73c27555 */
   private void mostrarDetalle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      String codTramite = request.getParameter("codTramite");
      System.out.println("CODIGO DE TRAMITE ========= " + codTramite);

            if ( codTramite != null && codTramite.trim().length() > 0 ) {

            int codTram = Integer.parseInt(codTramite.trim());  

                if ( codTram > 0 ) {

               Tramite miTram = Tramite.getTramiteBD(codTram);

                    if ( miTram != null ) {
                    request.setAttribute("miTramite", miTram);
                    request.getRequestDispatcher("detailTramite.jsp").forward(request, response);
                    }

                }

            }


   }


   /** Muestra el archivo que ha sido adjunto al momento de generar el tramite.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 43844dcc-b185-4a9f-8e32-3f29784a5cf4 */
   private void mostrarArchivo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       
    String codigo = request.getParameter("codTramite");


      if ( codigo != null && codigo.trim().length() > 0 ) {
      String miRuta = request.getServletContext().getInitParameter("directorioArchivos");

            if ( miRuta != null && miRuta.trim().length() > 0  )  {

            int valCod = Integer.parseInt(codigo);

            helpdesk.model.Tramite miTramite = helpdesk.model.Tramite.getTramiteBD(valCod);

                if ( miTramite != null && miTramite.getArchivo() != null ) {
                helpdesk.model.TramiteArchivo archivo = miTramite.getArchivo(); 

                InputStream is = archivo.getFichero(miRuta);

                    if ( is != null ) {
                    response.setContentType(archivo.getTipoContenido());
                    response.setHeader("Content-Disposition", "Content-Disposition: attachment; filename=" + archivo.getArchivo());

			int read = 0;
			byte[] bytes = new byte[1024];

			OutputStream os = response.getOutputStream();

				while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
				}

			os.flush();
			os.close();

                    }

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
    * @pdOid 5d2437b4-63b3-4f78-9f45-4ccd18f2c873 */
    @Override
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      HttpSession miObjSession = request.getSession(false);
      
      if ( miObjSession != null ) {

      Object objTrab = miObjSession.getAttribute("sTrabajador");
      Object objCargo = miObjSession.getAttribute("sCargo");

            if ( ( objTrab != null && objTrab instanceof Trabajador ) &&
                 ( objCargo != null && objCargo instanceof Cargo ) ) {

            String opcion = request.getParameter("opcion");

                if ( opcion != null && opcion.trim().equals("_verDetalle") == true  ) {
                    mostrarDetalle(request, response);
                }
                else if (opcion != null && opcion.trim().equals("_verArchivo") == true  ) {
                    mostrarArchivo(request, response);
                }
            
            }

      }

   }

   /** Called by the server (via the service method) to allow a servlet to handle a POST request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 45db195b-2f81-4f39-8399-cf57114b1b91 */
    @Override
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

   }

}