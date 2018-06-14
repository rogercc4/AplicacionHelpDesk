/***********************************************************************
 * Module:  ControlMensaje.java
 * Author:  rcontreras
 * Purpose: Defines the Class ControlMensaje
 ***********************************************************************/

package helpdesk.web;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/** Sirve para controlar las operaciones de la clase Mensaje. La clase Mensaje es la que sirve
 * para representar los mensajes que son enviados durante el proceso de atencion de una solicitud
 *
 * @pdOid e0f76bc5-db59-4913-81b0-aea740518f4e */
public class ControlMensaje extends javax.servlet.http.HttpServlet {
   /** Permite visualizar el archivo que fue adjuntado durante el momento que
    * se envio un mensaje.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 3cf377cd-46b5-4f48-af87-8c8871abda10 */
   private void visualizarArchivo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      String codigo = request.getParameter("codMensaje");

      if ( codigo != null && codigo.trim().length() > 0 ) {
      String miRuta = request.getServletContext().getInitParameter("directorioArchivos");

            if ( miRuta != null && miRuta.trim().length() > 0  )  {
            helpdesk.model.Mensaje miMensaje = helpdesk.model.Mensaje.getMensajeBD(Integer.parseInt(codigo));

                if ( miMensaje != null ) {
                InputStream is = miMensaje.getFichero(miRuta);

                    if ( is != null ) {
                    response.setContentType(miMensaje.getTipoContenidoAdjunto());
                    response.setHeader("Content-Disposition", "Content-Disposition: attachment; filename=" + miMensaje.getArchivoAdjunto());

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
    * @pdOid 2e6bc92f-a261-4570-9300-4cb584476ac0 */
    @Override
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
   HttpSession miSesion = request.getSession(false);
   String miOpcion = request.getParameter("opcion");

        if ( miSesion != null && miOpcion != null ) {

            if ( miOpcion.trim().equals("_visualizarArchivo")  )
                visualizarArchivo(request, response);
        }
   
   }

   /** Called by the server (via the service method) to allow a servlet to handle a POST request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid e2f4a45b-b30e-4fd6-865b-3c2c6b77f349 */
    @Override
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
   }

}