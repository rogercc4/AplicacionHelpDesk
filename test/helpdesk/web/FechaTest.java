/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package helpdesk.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rcontreras
 */
public class FechaTest {

    public FechaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getFechaActual method, of class Fecha.
     */
    @Test
    public void testGetFechaActual() {
        
        System.out.println("getFechaActual");
        Fecha instance = new Fecha();        
        System.out.println( instance.getFechaHoraActual() );
        
    }

}