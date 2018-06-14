/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpdesk.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*; 
import javax.servlet.http.*;
import helpdesk.model.*;

/**
 *
 * @author rcontreras
 */
public class VerificarLogin extends HttpServlet {
   
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

        if ( miSesion != null && request.getParameter("selectCargo") != null ) {
        String codCargo = request.getParameter("selectCargo");
        Cargo miCargo = Cargo.getCargoBD(Integer.parseInt(codCargo));
        miSesion.setAttribute("sCargo", miCargo);
        response.sendRedirect("sistemaHelpDesk.jsp");
        }
        else {

        String usuario = request.getParameter("txtUsuario");
        String clave = request.getParameter("txtClave");        

            try {
                Login miLogin = new Login(usuario);
                miLogin.setClave(clave);
                miLogin.validarLogin();                
                miSesion = request.getSession();

                Trabajador miTrabajador = Trabajador.getTrabajadorBD(usuario);                
                miSesion.setAttribute("sTrabajador", miTrabajador );                
                
                java.util.ArrayList<Cargo> misCargos = miLogin.getCargosLogin();
                request.setAttribute("cargosLogin", misCargos);
                
                RequestDispatcher rqd = request.getRequestDispatcher("selectCargo.jsp");

                rqd.forward(request, response);
                
            }
            catch (HelpDeskException ex) {
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", "index.jsp");
            request.setAttribute("msgTarget", "_parent");
            RequestDispatcher rpd = request.getRequestDispatcher("mensajeSistema.jsp");
            rpd.forward(request, response);
            }

        }

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
