/***********************************************************************
 * Module:  ControlBuscador.java
 * Author:  rcontreras
 * Purpose: Defines the Class ControlBuscador
 ***********************************************************************/

package helpdesk.web;

import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import helpdesk.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/** @pdOid c436bff9-1755-4f71-a9b9-ff6f5a10d08f */
public class ControlBuscador extends javax.servlet.http.HttpServlet {
   /** @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 8555eea8-f03b-4cd9-bce6-9ae3b916b615 */
   private void mostrarFormMisReqBuscador(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      java.util.ArrayList<TipoTramite> tiposTramite = new java.util.ArrayList<TipoTramite>(); 

      for(ValorTipoTramite valor : ValorTipoTramite.values())  {
      tiposTramite.add(TipoTramite.getTipoTramiteBD(valor));
      }

      request.setAttribute("tiposTramite", tiposTramite);

      request.getRequestDispatcher("formBusqMisRequerimientos.jsp").forward(request, response);


   }
   
    /** Muestra los requerimientos de un determinado trabajador en base a la
    * información que se tiene del último tramite.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid e258af84-431d-49f7-9220-f7482223b4d9 */
   private void mostrarMisReqPorUltimoTramite(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {
            // TODO: implement
            Thread.sleep(600); 

            HttpSession miSesion = request.getSession(false);
            Trabajador miTrab = (Trabajador)miSesion.getAttribute("sTrabajador");
            String parCodTram = request.getParameter("codTramite");
            
            int tamanioPagina = Integer.parseInt(request.getServletContext().getInitParameter("tamanioPagina"));
            String parNumPag = request.getParameter("numPag");
            int numPag = 1; 

            TipoTramite ultTram = null; 

                if ( parCodTram != null && parCodTram.trim().length() > 0  ) {
                ultTram = TipoTramite.getTipoTramiteBD(Integer.parseInt(parCodTram.trim())); 
                }

                if ( parNumPag != null && parNumPag.trim().length() > 0 ) {
                numPag = Integer.parseInt(parNumPag); 
                }
            
            String miFec1 = request.getParameter("fechaInicio");
            String miFec2 = request.getParameter("fechaFin");

            Locale loc = new Locale("es", "PE");
            java.text.DateFormat df = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM, loc);
            df.setLenient(false);
            Date fec1 = df.parse(miFec1);
            Date fec2 = df.parse(miFec2);
            Date[] fechas = new Date[2];
            fechas[0] = fec1;
            fechas[1] = fec2;

            Buscador busq = new Buscador();
            busq.setPagina(numPag);
            busq.setTamanioPagina(tamanioPagina);             
            java.util.ArrayList<Solicitud> misSolicitudes = busq.getMisReqPorUltimoTramite(miTrab, ultTram, fechas);

            if ( misSolicitudes == null  ) {            
            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", "No se han encontrado solicitudes");
            this.generarRespuestaXMLOperacion(response, parametros);
            }
            else {
            request.setAttribute("misSolicitudes", misSolicitudes); 
            request.getRequestDispatcher("resultadosBusqueda.inc.jsp").forward(request, response);
            }

        }
        catch (ParseException ex) {
        Logger.getLogger(ControlBuscador.class.getName()).log(Level.SEVERE, null, ex);
        HashMap parametros = new HashMap();
        parametros.put("OPERACION_CORRECTA", "false");
        parametros.put("MENSAJE_OPERACION", "Ingrese las fechas correctamente");
        this.generarRespuestaXMLOperacion(response, parametros);
        }
        catch (Exception ex) {
            Logger.getLogger(ControlBuscador.class.getName()).log(Level.SEVERE, null, ex);
            
            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", ex.getMessage());
            this.generarRespuestaXMLOperacion(response, parametros);
        }
        

   }

    /** Muestra el detalle de una solicitud que ha sido seleccionada
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 2fc38d64-3497-4715-b77d-96ce6f61377d */
   private void mostrarDetalleSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      String parCodSolic = request.getParameter("codSolic");
      
      if ( parCodSolic != null && parCodSolic.trim().length() > 0 ) {
      int codSolic = Integer.parseInt(parCodSolic);
      Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolic);

      request.setAttribute("miSolicitud", miSolicitud);
      request.getRequestDispatcher("detalleSolicitud.inc.jsp").forward(request, response);
      }

   }

   /** Muestra el número de solicitudes que existen por cada activo que se ha
    * definido para realizar la clasificación de las solicitudes
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 6df94d1a-b24a-44ab-ba6a-d276b630cd38 */
   private void mostrarNumSolicitudesPorActivo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {
            // TODO: implement
            String parFec1 = request.getParameter("fechaInicio");
            String parFec2 = request.getParameter("fechaFin");
            Locale loc = new Locale("es", "PE");
            java.text.DateFormat df = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM, loc);
            df.setLenient(false);
            Date fec1 = df.parse(parFec1);
            Date fec2 = df.parse(parFec2);
            
            Object[][] resultados = Activo.getNumSolicitudesPorActivo(fec1, fec2);

            Object[][] rst = resultados ; 

                if ( resultados != null ) {

                int total = 0;

                for (Object[] fila : rst ) {
                total += Integer.parseInt(fila[2].toString());
                }
                
                DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();
                Document docDOM = docBuilder.newDocument();
                Element elementoRaiz = docDOM.createElement("resultadosBusqueda");
                docDOM.appendChild(elementoRaiz);

                double valorPorc = 0.0;

                    for ( Object[] fila : resultados ) {
                    Element activo =  docDOM.createElement("activo"); 

                    Element elCodigo = docDOM.createElement("codigo");
                    elCodigo.appendChild(docDOM.createTextNode(fila[0].toString()));

                    Element elDescripcion = docDOM.createElement("descripcion");
                    elDescripcion.appendChild(docDOM.createTextNode(fila[1].toString()));
                    
                    Element elCantidad = docDOM.createElement("numero");

                    valorPorc = (Double.valueOf(fila[2].toString()) / total ) * 100 ;

                    System.out.println(valorPorc);

                    elCantidad.appendChild(docDOM.createTextNode( String.valueOf(valorPorc)));

                    activo.appendChild(elCodigo);
                    activo.appendChild(elDescripcion);
                    activo.appendChild(elCantidad); 
                    
                    elementoRaiz.appendChild(activo); 
                    }

                    PrintWriter out = response.getWriter();

                    OutputFormat format = new OutputFormat(docDOM);
                    format.setIndenting(true);
                    format.setEncoding("ISO-8859-1");
                    XMLSerializer serializadorXML = new XMLSerializer();
                    StringWriter writer = new StringWriter();
                    serializadorXML.setOutputCharStream(writer);
                    serializadorXML.setOutputFormat(format);
                    serializadorXML.serialize(docDOM);
                    writer.close();

                    response.setContentType("text/xml");
                    out.println(writer.toString());

                }
                else {
                HashMap parametros = new HashMap();
                parametros.put("OPERACION_CORRECTA", "false");
                parametros.put("MENSAJE_OPERACION", "No se han encontrado solicitudes");
                this.generarRespuestaXMLOperacion(response, parametros);
                }

        }
        catch (ParseException ex) {
        Logger.getLogger(ControlBuscador.class.getName()).log(Level.SEVERE, null, ex);
        HashMap parametros = new HashMap();
        parametros.put("OPERACION_CORRECTA", "false");
        parametros.put("MENSAJE_OPERACION", "Ingrese las fechas correctamente");
        this.generarRespuestaXMLOperacion(response, parametros);
        }
        catch (Exception ex) {
            Logger.getLogger(ControlBuscador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", ex.getMessage());
            this.generarRespuestaXMLOperacion(response, parametros);
        }

   }

   /** Called by the server (via the service method) to allow a servlet to handle a GET request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 9efb3e7b-54e5-4864-9e53-8dfc7b04ace4 */
   @Override
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      HttpSession miSesion = request.getSession(false);
      String opcion = request.getParameter("opcion"); 

            if ( miSesion != null && opcion != null ) {

                if ( opcion.trim().equals("_mostrarFormBusqMisReq") == true ) {
                mostrarFormMisReqBuscador(request, response);
                }

                if ( opcion.trim().equals("_mostrarDetalleSolicitud") == true ) {
                mostrarDetalleSolicitud(request, response);
                }


            }

   }

   /** Called by the server (via the service method) to allow a servlet to handle a POST request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 5636ec26-7b95-44bc-86af-fd0c02f723f1 */
   @Override
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      HttpSession miSesion = request.getSession(false);
      String opcion = request.getParameter("opcion");

            if ( miSesion != null && opcion != null ) {

                if ( opcion.trim().equals("_mostrarMisReqPorUltimoTramite") == true ) {
                mostrarMisReqPorUltimoTramite(request, response);
                }                

                if ( opcion.trim().equals("_mostrarNumSolicitudesPorActivo") == true ) {
                mostrarNumSolicitudesPorActivo(request, response);
                }

            }

   }
   
   private void generarRespuestaXMLOperacion(javax.servlet.http.HttpServletResponse response, java.util.Map parametros) {
        {
            PrintWriter out = null;
            try {
                DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();
                Document docDOM = docBuilder.newDocument();
                Element elementoRaiz = docDOM.createElement("respuestaOperacion");
                docDOM.appendChild(elementoRaiz);
                String operacionCorrecta = parametros.get("OPERACION_CORRECTA").toString();
                String mensaje = parametros.get("MENSAJE_OPERACION").toString();
                Element elResultado = docDOM.createElement("resultado");
                elResultado.appendChild(docDOM.createTextNode(operacionCorrecta));
                elementoRaiz.appendChild(elResultado);
                Element elMensaje = docDOM.createElement("mensaje");
                elMensaje.appendChild(docDOM.createTextNode(mensaje));
                elementoRaiz.appendChild(elMensaje);

                out = response.getWriter();

                OutputFormat format = new OutputFormat(docDOM);
                format.setIndenting(true);
                format.setEncoding("ISO-8859-1");
                XMLSerializer serializadorXML = new XMLSerializer();
                StringWriter writer = new StringWriter();
                serializadorXML.setOutputCharStream(writer);
                serializadorXML.setOutputFormat(format);
                serializadorXML.serialize(docDOM);
                writer.close();
                response.setContentType("text/xml");
                out.println(writer.toString());

            } catch (IOException ex) {
                Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.close();
            }
        }

   }


}