/***********************************************************************
 * Module:  RespuestasXML.java
 * Author:  rcontreras
 * Purpose: Defines the Class RespuestasXML
 ***********************************************************************/

package helpdesk.web;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import helpdesk.model.*; 

/** Esta clase permite gestionar las solicitudes, en las cuales las respuestas van a ser enviadas en formato XML.
 *
 * @pdOid 7787fee8-9575-4eb3-b2dc-3494707d8879 */
public class RespuestasXML extends javax.servlet.http.HttpServlet {
   /** Muestra los activos
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid d8a5cda5-35c2-4acb-9e19-63b1bf2d9008 */
   private void mostrarActivos(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
   throws javax.servlet.ServletException, java.io.IOException {
        try {
            XMLSerializer serializadorXML = null;
            OutputFormat formatoSalida = null;
            serializadorXML = new XMLSerializer();
            formatoSalida = new OutputFormat();
            // 1. Establecer el formato
            formatoSalida.setEncoding("ISO-8859-1");
            formatoSalida.setVersion("1.0");
            formatoSalida.setIndenting(true);
            formatoSalida.setIndent(4);

            ArrayList<Activo> misActivos = Activo.getListaActivosBD(FiltroRegistros.ACTIVADO);
            Iterator<Activo> i = misActivos.iterator();
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();
            Element root = doc.createElement("root");
            doc.appendChild(root);
            Element listado = doc.createElement("listadoActivos");
            root.appendChild(listado);
            
            while(i.hasNext()) {
                Activo activo =i.next();
                Element bien = doc.createElement("activo");
                listado.appendChild(bien);
                Element codigo = doc.createElement("codigo");
                codigo.appendChild(doc.createTextNode(Integer.toString(activo.getCodigo())));
                bien.appendChild(codigo);
                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(activo.getNombre()));
                bien.appendChild(nombre);
            }

            // 2. Definir un objeto donde se generara el codigo
            StringWriter writer = new StringWriter();
            serializadorXML.setOutputCharStream(writer);
            // 3. Aplicar el formato
            serializadorXML.setOutputFormat(formatoSalida);
            // 4. Serializar documento XML
            serializadorXML.serialize(doc);
            writer.close();

            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            out.println(writer.toString());

        }  catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }catch (IOException ioEx) {
            System.out.println("Error : " + ioEx);
        }

   }

   /** Muestra las categorias en formato XML. Las categorías se van a mostrar deacuerdo a la información del activo que se envie.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 7cba0688-bd02-4ca7-bd36-7517caa8fe98 */
   private void mostrarCategorias(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {

           String codActivo = request.getParameter("codActivo");
           Activo activoSelect = null;
           
           if ( codActivo != null ) {

               try {
               activoSelect = Activo.getActivoBD(Integer.parseInt(codActivo.trim()));
               }
               catch(Exception ex) {
               Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
               activoSelect = null;
               }
               
           }

            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();

            Document docDOM = docBuilder.newDocument();

            Element elementoRaiz = docDOM.createElement("listadoCategorias");
            docDOM.appendChild(elementoRaiz);

            if ( activoSelect != null ) {

            ArrayList<Categoria> categorias = activoSelect.getCategorias();

                if ( categorias != null ) {
                Iterator<Categoria> iCategorias = categorias.iterator();
                Categoria miCat = null;

                    while ( iCategorias.hasNext() ) {
                    miCat = iCategorias.next();
                    Element elCategoria = docDOM.createElement("categoria");
                    elementoRaiz.appendChild(elCategoria);

                    Element elCodigo = docDOM.createElement("codigo");
                    elCategoria.appendChild(elCodigo);
                    elCodigo.appendChild(docDOM.createTextNode(String.valueOf(miCat.getCodigo())));

                    Element elNombre = docDOM.createElement("nombre");
                    elCategoria.appendChild(elNombre);
                    elNombre.appendChild(docDOM.createTextNode(String.valueOf(miCat.getNombre())));

                    }

                }

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

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   }

   /** Muestra las sub-categorias de acuerdo a la categoria que se ha seleccionado.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 712bfe94-4e71-406d-8554-6a25721babe1 */
   private void mostrarSubCategorias(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

        try {

           String codCat = request.getParameter("codCategoria");
           Categoria categoriaSelect = null; 

           if ( codCat != null ) {

               try {
               categoriaSelect = Categoria.getCategoriaBD(Integer.parseInt(codCat.trim()));
               }
               catch(Exception ex) {
               Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
               categoriaSelect = null;
               }

           }

            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();

            Document docDOM = docBuilder.newDocument();

            Element elementoRaiz = docDOM.createElement("listadoSubCategorias");
            docDOM.appendChild(elementoRaiz);

            if ( categoriaSelect != null ) {

            ArrayList<SubCategoria> subCategorias = categoriaSelect.getSubCategorias();

                if ( subCategorias != null ) {
                Iterator<SubCategoria> iSubCategorias = subCategorias.iterator();
                SubCategoria miSubCat = null;

                    while ( iSubCategorias.hasNext() ) {
                    miSubCat = iSubCategorias.next();
                    Element elCategoria = docDOM.createElement("subCategoria");
                    elementoRaiz.appendChild(elCategoria);

                    Element elCodigo = docDOM.createElement("codigo");
                    elCategoria.appendChild(elCodigo);
                    elCodigo.appendChild(docDOM.createTextNode(String.valueOf(miSubCat.getCodigo())));

                    Element elNombre = docDOM.createElement("nombre");
                    elCategoria.appendChild(elNombre);
                    elNombre.appendChild(docDOM.createTextNode(String.valueOf(miSubCat.getNombre())));

                    }

                }

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

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
        }

   }

   /** Muestra las prioridades en las que puede ser clasificada una solicitud
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 5db22b88-40cf-4076-b970-cdab362382b9 */
   private void mostrarPrioridades(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {
            // TODO: implement
            ArrayList<Prioridad> prioridades = Prioridad.getPrioridades();
            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();
            Document docDOM = docBuilder.newDocument();
            Element elementoRaiz = docDOM.createElement("listadoPrioridades");
            docDOM.appendChild(elementoRaiz);

                if ( prioridades != null  ) {
                Iterator<Prioridad> iPrioridades = prioridades.iterator();
                Prioridad miPrior = null; 

                    while ( iPrioridades.hasNext() ) {
                    miPrior = iPrioridades.next();
                    Element elCategoria = docDOM.createElement("prioridad");
                    elementoRaiz.appendChild(elCategoria);

                    Element elCodigo = docDOM.createElement("codigo");
                    elCategoria.appendChild(elCodigo);
                    elCodigo.appendChild(docDOM.createTextNode(String.valueOf(miPrior.getCodigo())));

                    Element elNombre = docDOM.createElement("nombre");
                    elCategoria.appendChild(elNombre);
                    elNombre.appendChild(docDOM.createTextNode(String.valueOf(miPrior.getNombre())));

                    Element elOrden = docDOM.createElement("orden");
                    elCategoria.appendChild(elOrden);
                    elOrden.appendChild(docDOM.createTextNode(String.valueOf(miPrior.getOrden())));

                    }

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
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
        }

   }

    /** Muestra los responsables de dar atencion a una solicitud en base a una sub-categoria señalada
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid f7ab8753-53bf-4821-b25e-8f098682e377 */
   private void mostrarCargosSubCategoria(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       
        try {

           String codSubCat = request.getParameter("codSubCategoria");
           SubCategoria subCategoriaSelect = null;

           if ( codSubCat != null ) {

               try {
               subCategoriaSelect = SubCategoria.getSubCategoriaBD(Integer.parseInt(codSubCat.trim()));
               }
               catch(Exception ex) {
               Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
               subCategoriaSelect = null;
               }

           }

            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();

            Document docDOM = docBuilder.newDocument();

            Element elementoRaiz = docDOM.createElement("listadoCargosSubCategorias");
            docDOM.appendChild(elementoRaiz);

            if ( subCategoriaSelect != null ) {

            ArrayList<Cargo> cargos = subCategoriaSelect.getCargosResponsable();

                if ( cargos != null ) {
                Iterator<Cargo> icargos = cargos.iterator();
                Cargo miCargo = null;

                    while ( icargos.hasNext() ) {
                    miCargo = icargos.next();
                    Element elCategoria = docDOM.createElement("cargo");
                    elementoRaiz.appendChild(elCategoria);

                    Element elCodigo = docDOM.createElement("codigo");
                    elCategoria.appendChild(elCodigo);
                    elCodigo.appendChild(docDOM.createTextNode(String.valueOf(miCargo.getCodCargo())));

                    Element elNombre = docDOM.createElement("nombre");
                    elCategoria.appendChild(elNombre);
                    elNombre.appendChild(docDOM.createTextNode(String.valueOf(miCargo.getNombre())));

                    }

                }

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

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
        }


   }

   /** Muestra los trabajadores responsables de un cargo
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid ce6abcca-b689-49e3-bacf-5b0ea13efea4 */
   private void mostrarResponsablesCargo(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      
        try {

           String codCargo = request.getParameter("codCargo");
           Cargo cargoSelect = null;

           if ( codCargo != null ) {

               try {
               cargoSelect = Cargo.getCargoBD(Integer.parseInt(codCargo.trim()));
               }
               catch(Exception ex) {
               Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
               cargoSelect = null;
               }

           }

            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();

            Document docDOM = docBuilder.newDocument();

            Element elementoRaiz = docDOM.createElement("responsablesCargo");
            docDOM.appendChild(elementoRaiz);

            if ( cargoSelect != null ) {

            ArrayList<Trabajador> trabajadores = cargoSelect.getTrabajadores();

                if ( trabajadores != null ) {
                Iterator<Trabajador> iTrabajador = trabajadores.iterator();
                Trabajador miTrabajador = null;

                    while ( iTrabajador.hasNext() ) {
                    miTrabajador = iTrabajador.next();
                    Element elTrabajador = docDOM.createElement("trabajador");
                    elementoRaiz.appendChild(elTrabajador);

                    Element elUsuario = docDOM.createElement("usuario");
                    elTrabajador.appendChild(elUsuario);
                    elUsuario.appendChild(docDOM.createTextNode(miTrabajador.getUsuario()));

                    Element elNombre = docDOM.createElement("nombre");
                    elTrabajador.appendChild(elNombre);
                    elNombre.appendChild(docDOM.createTextNode( miTrabajador.getNombre() + " " + miTrabajador.getApellido()));

                    }

                }

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

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
        }

   }


   /** @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 2f844bc7-52f9-429e-b24b-1244589eda50 */
   private void mostrarDetalleTarea(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
        try {

           String codTarea = request.getParameter("codTarea");
           Tarea tareaSelect = null;

           if ( codTarea != null ) {

               try {
               tareaSelect = Tarea.getTareaBD(Integer.parseInt(codTarea.trim()));
               }
               catch(Exception ex) {
               Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
               tareaSelect = null;
               }

           }

            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();

            Document docDOM = docBuilder.newDocument();

            Element elementoRaiz = docDOM.createElement("detalleTarea");
            docDOM.appendChild(elementoRaiz);

            if ( tareaSelect != null ) {
            Element elTarea = docDOM.createElement("tarea");
            elementoRaiz.appendChild(elTarea); 

            Element elCodigo = docDOM.createElement("codigo");
            elCodigo.appendChild(docDOM.createTextNode(String.valueOf(tareaSelect.getCodigo())));
            elTarea.appendChild(elCodigo);

            Element elDescripcion = docDOM.createElement("descripcion");
            elDescripcion.appendChild(docDOM.createTextNode(tareaSelect.getDescripcion()));
            elTarea.appendChild(elDescripcion);

            Locale loc = new Locale("es", "PE"); 
            java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.MEDIUM,
                                                                                java.text.DateFormat.SHORT, loc);

            
            Element elFecInicio = docDOM.createElement("fechaInicio");
            elFecInicio.appendChild(docDOM.createTextNode( df.format(tareaSelect.getFechaInicio())));
            elTarea.appendChild(elFecInicio);

            Element elFecFin = docDOM.createElement("fechaFin");
            elFecFin.appendChild(docDOM.createTextNode( df.format(tareaSelect.getFechaFin())));
            elTarea.appendChild(elFecFin);
            
            Element elNombre = docDOM.createElement("nombreTrabajador");
            elNombre.appendChild(docDOM.createTextNode( tareaSelect.getNombreTrabajador()) );
            elTarea.appendChild(elNombre);

            Element elCargo = docDOM.createElement("cargo");
            elCargo.appendChild(docDOM.createTextNode( tareaSelect.getCargo() ) );
            elTarea.appendChild(elCargo);

            Element elArea = docDOM.createElement("area");
            elArea.appendChild(docDOM.createTextNode( tareaSelect.getArea() ) );
            elTarea.appendChild(elArea);

            Element elCodTramite = docDOM.createElement("codTramite");
            elCodTramite.appendChild(docDOM.createTextNode(  String.valueOf(tareaSelect.getCodTramite()) ) );
            elTarea.appendChild(elCodTramite);

            Element elUsuario = docDOM.createElement("usuario");
            elUsuario.appendChild(docDOM.createTextNode( tareaSelect.getUsuario() ) );
            elTarea.appendChild(elUsuario);

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

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
   
   
   /** @param request 
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid e48262e5-b1a6-4ef1-a144-807f81da171c */
   private void mostrarDetalleMensaje(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    // TODO: implement
       
        try {

           String codMensaje = request.getParameter("codMensaje");
           Mensaje MensajeSelect = null;

           if ( codMensaje != null ) {

               try {
               MensajeSelect = Mensaje.getMensajeBD(Integer.parseInt(codMensaje.trim()));
               }
               catch(Exception ex) {
               Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
               MensajeSelect = null;
               }

           }

            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factoryDoc.newDocumentBuilder();

            Document docDOM = docBuilder.newDocument();

            Element elementoRaiz = docDOM.createElement("detalleMensaje");
            docDOM.appendChild(elementoRaiz);

            if ( MensajeSelect != null ) {
            Element elMensaje = docDOM.createElement("mensaje");
            elementoRaiz.appendChild(elMensaje);

            Element elCodigo = docDOM.createElement("codigo");
            elCodigo.appendChild(docDOM.createTextNode(String.valueOf(MensajeSelect.getCodigo())));
            elMensaje.appendChild(elCodigo);

            Element elDescripcion = docDOM.createElement("descripcion");
            elDescripcion.appendChild(docDOM.createTextNode(MensajeSelect.getDescripcion()));
            elMensaje.appendChild(elDescripcion);

            Locale loc = new Locale("es", "PE");
            java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.MEDIUM,
                                                                                java.text.DateFormat.SHORT, loc);


            Element elFecRegistro = docDOM.createElement("fechaRegistro");
            elFecRegistro.appendChild(docDOM.createTextNode( df.format(MensajeSelect.getFecRegistro())));
            elMensaje.appendChild(elFecRegistro);

            Element elTrabajadorOrigen = docDOM.createElement("trabajadorOrigen");
            Trabajador trabOrig = MensajeSelect.getTrabajadorOrigen(); 
            elTrabajadorOrigen.appendChild(docDOM.createTextNode( trabOrig.getNombre() + " " + trabOrig.getApellido()));
            elMensaje.appendChild(elTrabajadorOrigen);

            Element elTrabajadorDestino = docDOM.createElement("trabajadorDestino");
            Trabajador trabDest = MensajeSelect.getTrabajadorDestino();
            elTrabajadorDestino.appendChild(docDOM.createTextNode( trabDest.getNombre() + " " + trabDest.getApellido()) );
            elMensaje.appendChild(elTrabajadorDestino);

            Element elNombreAdjunto = docDOM.createElement("nombreAdjunto");
            elNombreAdjunto.appendChild(docDOM.createTextNode( MensajeSelect.getNombreAdjunto() ) );
            elMensaje.appendChild(elNombreAdjunto);

            
            Element elCodTramite = docDOM.createElement("codTramite");
            elCodTramite.appendChild(docDOM.createTextNode(  String.valueOf(MensajeSelect.getCodTramite()) ) );
            elMensaje.appendChild(elCodTramite);

            Element elAsunto = docDOM.createElement("asunto");
            elAsunto.appendChild(docDOM.createTextNode( MensajeSelect.getAsunto() ) );
            elMensaje.appendChild(elAsunto);

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

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
        }

   }

   /** Called by the server (via the service method) to allow a servlet to handle a GET request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 0d042180-71a9-40ef-ba34-b1c1e8de705f */
    @Override
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      
       HttpSession miSesion = request.getSession(false);

       String metodo = request.getParameter("callMetodo");

            if ( metodo != null && miSesion != null ) {

                try {
                    Thread.sleep(600);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RespuestasXML.class.getName()).log(Level.SEVERE, null, ex);
                }

                if ( metodo.toString().trim().equals("_callMostrarActivos") ) 
                    mostrarActivos(request, response);

                if ( metodo.toString().trim().equals("_callMostrarCategorias") )
                    mostrarCategorias(request, response);

                if ( metodo.toString().trim().equals("_callMostrarSubCategorias") )
                    mostrarSubCategorias(request, response);                

                if ( metodo.toString().trim().equals("_callMostrarPrioridades") )
                    mostrarPrioridades(request, response);                
                
                if ( metodo.toString().trim().equals("_callMostrarCargosSubCategoria") )
                    mostrarCargosSubCategoria(request, response);
 
                if ( metodo.toString().trim().equals("_callMostrarResponsablesCargo") )
                    mostrarResponsablesCargo(request, response);

                if ( metodo.toString().trim().equals("_callMostrarDetalleTarea") )
                    mostrarDetalleTarea(request, response);
                        //mostrarDetalleMensaje
                
                if ( metodo.toString().trim().equals("_callMostrarDetalleMensaje") )
                    mostrarDetalleMensaje(request, response);

            }       
        
    }

   /** Called by the server (via the service method) to allow a servlet to handle a POST request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid fde1a097-ba26-46e8-b7dc-a355895e7277 */
    @Override
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      
       
   }

}