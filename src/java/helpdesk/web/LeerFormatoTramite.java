/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpdesk.web;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import helpdesk.model.*;

/**
 *
 * @author rcontreras
 */
public class LeerFormatoTramite extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        HttpSession miSesion = request.getSession(false);

        if ( miSesion != null ) {

	try {
	String codigo = request.getParameter("codigo");
	InputStream is = null ;
	String extension = null ;

		if ( codigo != null && codigo.trim().length() > 0 ) {
		codigo = codigo.trim();

                FormatoTramite formato = FormatoTramite.getFormatoTramiteBD(codigo);
                is = formato.getArchivo();
                extension = formato.getExtension();

                response.setContentType(extension);

			if ( is != null ) {
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
	catch(Exception e) {
	e.printStackTrace();
	}

        }

    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
