/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpdesk.web;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*; 
import helpdesk.model.data.*; 

/**
 *
 * @author rcontreras
 */
public class ConexionBD  implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
            Conexion miConexion = Conexion.getInstanceConexion();
            ServletContext sc = sce.getServletContext();
            miConexion.setBaseDatos(sc.getInitParameter("baseDatos"));
            miConexion.setClave(sc.getInitParameter("clave"));
            miConexion.setPuerto(sc.getInitParameter("puerto"));
            miConexion.setServidor(sc.getInitParameter("servidor"));
            miConexion.setUsuario(sc.getInitParameter("usuario"));
        try {
            miConexion.abrirConexion();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    public void contextDestroyed(ServletContextEvent sce) {
    
    }
}