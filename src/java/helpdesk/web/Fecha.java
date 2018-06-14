/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpdesk.web;
import java.util.*;
import java.text.*;


/**
 *
 * @author rcontreras
 */
public class Fecha {

    private String fechaActual;
    protected String fechaHoraActual;
    

    /**
     * Get the value of fechaHoraActual
     *
     * @return the value of fechaHoraActual
     */
    public String getFechaHoraActual() {
    Date miFecha = new Date();    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:m:s a");    
    fechaHoraActual = sdf.format(miFecha);  
    return fechaHoraActual;    
    }

    /**
     * Get the value of fechaActual
     *
     * @return the value of fechaActual
     */
    public String getFechaActual() {
    Date miFecha = new Date();
    Locale local = new Locale("es", "PE"); 
    DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, local);
    fechaActual = df.format(miFecha).toString();

    return fechaActual;
    }

    

}
