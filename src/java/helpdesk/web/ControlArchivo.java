/***********************************************************************
 * Module:  ControlArchivo.java
 * Author:  Roger
 * Purpose: Defines the Class ControlArchivo
 ***********************************************************************/

package helpdesk.web;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import helpdesk.model.*;
import java.io.*; 

/** Esta clase representa a la clase control encargada de las tareas relacionados con la clase "Archivo".
 *
 * Un Archivo es un fichero que se adjunta a una solicitud al momento de que esta es generada.
 *
 * @pdOid 2ec2da9a-4d58-4ad9-8c0e-3a35a96b141b */
public class ControlArchivo extends javax.servlet.http.HttpServlet {
    
   /** Permite visualizar un archivo que ha sido adjuntado a una solicitud
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 268c3605-d241-4e70-99a0-b3550b219d7f */
   private void visualizarArchivo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
           throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      String codigo = request.getParameter("codArchivo");

      if ( codigo != null && codigo.trim().length() > 0 ) {       
      String miRuta = request.getServletContext().getInitParameter("directorioArchivos");

            if ( miRuta != null && miRuta.trim().length() > 0  )  {
            Archivo miArchivo = Archivo.getArchivoBD(Integer.parseInt(codigo));

                if ( miArchivo != null ) {
                InputStream is = miArchivo.getFichero(miRuta);                

                    if ( is != null ) {
                    response.setContentType(miArchivo.getTipoContenido());
                    response.setHeader("Content-Disposition", "Content-Disposition: attachment; filename=" + miArchivo.getArchivo());
                    
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
    * @pdOid 146e1666-9c18-4832-a55c-18bb617e8b63 */
   @Override
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
           throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
   HttpSession miSesion = request.getSession(false);
   String miOpcion = request.getParameter("opcion");

        if ( miSesion != null && miOpcion != null ) {

            if ( miOpcion.trim().equals("_verArchivo")  )
                visualizarArchivo(request, response);


        }

   }

   /** Called by the server (via the service method) to allow a servlet to handle a POST request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid f499a79d-0dc9-4bea-8c2c-65f70d84c1d5 */
   @Override
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
           throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

   }

}