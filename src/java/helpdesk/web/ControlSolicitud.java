/***********************************************************************
 * Module:  ControlSolicitud.java
 * Author:  Roger
 * Purpose: Defines the Class ControlSolicitud
 ***********************************************************************/

package helpdesk.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import helpdesk.model.*;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.FileUpload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;

/** Esta clase permite procesar las operaciones que generan respuestas, las cuales se van hacer visibles en los JSP que correspondan.
 *
 * Aqui se trata todas las operaciones que se puedan realizar con las solicitudes.
 *
 *
 * @pdOid ff8dc64c-5e5c-4760-8fc3-c652e12a031b */
public class ControlSolicitud extends javax.servlet.http.HttpServlet {
   /** Permite registrar una solicitud.
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid 1f1a83fc-f20f-4a5d-8710-3583ce29cf4c */
   private void registrarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      // TODO: implement
    String asunto="", detalle="", codTipoSolic="", adjuntos="", vistoBueno="", nombreArchivo="" ;
    Archivo miArchivo = null ;
    boolean isProcesoArchivosCorrecto = false;

    try {
    HttpSession miSesion = request.getSession(false);
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    String directorioFiles = request.getServletContext().getInitParameter("directorioArchivos");
    double tamanioMaxFiles = Double.valueOf(request.getServletContext().getInitParameter("tamanioArchivos"));

        if ( miSesion !=null ) {

            if ( isMultipart == true ) {

            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

                while ( iter.hasNext() ) {
                FileItem item = (FileItem) iter.next();

                    if ( item.isFormField() ) {

                            if ( item.getFieldName().trim().equals("txtAsunto") ) {
                            asunto = item.getString();
                            }

                            if ( item.getFieldName().trim().equals("txtDetalle") ) {
                            detalle = item.getString();
                            }

                            if ( item.getFieldName().trim().equals("codTipoSolic") ) {
                            codTipoSolic = item.getString();
                            }

                            if ( item.getFieldName().trim().equals("txtAdjuntos") ) {
                            adjuntos = item.getString();
                            }

                            if ( item.getFieldName().trim().equals("chkVistoBueno") ) {
                            vistoBueno = item.getString();
                            }

                            if ( item.getFieldName().trim().equals("txtNombreArchivo") ) {
                            nombreArchivo = item.getString();
                            }

                    }
                    else {
                    
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    String contentType = item.getContentType();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();

                        if ( sizeInBytes <= 0 ) {
                        isProcesoArchivosCorrecto = true ;
                        }
                        else {
                        double tamanioMB = ((sizeInBytes)/1024)/1024;

                            if ( tamanioMB > tamanioMaxFiles )
                                throw new HelpDeskException("El archivo que se desea subir no "
                                                            + "debe pesar mas de " + tamanioMaxFiles + " MB");

                        File fichero = new File(fileName);

                            //if ( fichero.exists() ) {
                            File miFile = new File(directorioFiles, fichero.getName());
                            item.write(miFile);

                                if ( miFile.exists() ) {
                                String[] divNombres = fileName.split("\\.");
                                String nombreArchivoTemp = String.valueOf( new java.util.Date().getTime() );

                                    if ( divNombres.length > 1 ) {
                                    nombreArchivoTemp = nombreArchivoTemp + "." + divNombres[divNombres.length -1];
                                    }

                                boolean cambio = miFile.renameTo( new java.io.File(directorioFiles,  nombreArchivoTemp) );

                                    if ( cambio == true ) {
                                    miArchivo = new Archivo();
                                    miArchivo.setArchivo(nombreArchivoTemp);
                                    miArchivo.setFecha(new Date());
                                    miArchivo.setTipoContenido(contentType);

                                    isProcesoArchivosCorrecto = true;

                                    }

                                }


                            //}

                        }

                    }

                }

            }
            else {
            asunto = request.getParameter("txtAsunto");
            detalle = request.getParameter("txtDetalle");
            codTipoSolic = request.getParameter("codTipoSolic");
            adjuntos = request.getParameter("txtAdjuntos");
            vistoBueno = request.getParameter("chkVistoBueno");
            isProcesoArchivosCorrecto = true;
            }


                if ( isProcesoArchivosCorrecto == false )
                    throw new HelpDeskException("El archivo no pudo ser adjuntado");

            Solicitud miSolic = new Solicitud();

            miSolic.setAdjuntos(adjuntos);
            miSolic.setAsunto(asunto);
            miSolic.setCargo((Cargo)miSesion.getAttribute("sCargo"));
            miSolic.setDetalle(detalle);
            miSolic.setFecha(new Date());
            miSolic.setTipoSolicitud( new TipoSolicitud(Integer.parseInt( codTipoSolic )) );
            miSolic.setTrabajador((Trabajador)miSesion.getAttribute("sTrabajador"));

            Trabajador miTrabajador = new Trabajador();
            java.util.ArrayList<Archivo> misArchivos = null;

                if ( miArchivo != null )  {
                misArchivos = new java.util.ArrayList<Archivo>();
                miArchivo.setNombre(nombreArchivo);
                misArchivos.add(miArchivo);
                }

            miTrabajador.guardarSolicitud(miSolic, misArchivos, vistoBueno.trim().equals("1") );

            request.setAttribute("msgSistema", "La solicitud fue generada correctamente");
            request.setAttribute("msgurl", "controlSolicitud.do?opcion=_verSolicitudes");
            request.setAttribute("msgTarget", "_self");
            RequestDispatcher rpd = request.getRequestDispatcher("mensajeSistema.jsp");
            rpd.forward(request, response);

        }

    }
    catch (HelpDeskException ex) {
    Logger.getLogger(ControlSolicitud.class.getName()).log(Level.SEVERE, null, ex);

    request.setAttribute("msgSistema", ex.getMessage());
    request.setAttribute("msgurl", "controlSolicitud.do?opcion=_verSolicitudes");
    request.setAttribute("msgTarget", "_self");
    RequestDispatcher rpd = request.getRequestDispatcher("mensajeSistema.jsp");
    rpd.forward(request, response);
    }
    catch (Exception ex) {
    Logger.getLogger(ControlSolicitud.class.getName()).log(Level.SEVERE, null, ex);

    request.setAttribute("msgSistema", "La solicitud no pudo ser guardada");
    request.setAttribute("msgurl", "controlSolicitud.do?opcion=_verSolicitudes");
    request.setAttribute("msgTarget", "_self");
    RequestDispatcher rpd = request.getRequestDispatcher("mensajeSistema.jsp");
    rpd.forward(request, response);
    }

    }

   /** Muestra los tipos de solicitudes que pueden ser usados por la persona que desee registrar una solicitud.
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid 2263b9e0-0ca4-4638-a3a5-b37f7f485f0f */
   private void mostrarTiposSolic(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      // TODO: implement
    HttpSession miSesion = request.getSession(false);

        if ( miSesion != null ) {
        java.util.ArrayList<TipoSolicitud> misTiposSolicitud = TipoSolicitud.getTiposSolicitud(FiltroRegistros.ACTIVADO);
        miSesion.setAttribute("misTiposSolicitud", misTiposSolicitud);

        response.sendRedirect("selectTipoSolicitud.jsp");
        }
        else {
        response.sendRedirect("index.jsp");
        }
   }

   /** Permite definir con que tipo de solicitud se desea trabajar.
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid 5eb03646-b218-4ad2-9913-be10bf2fc0e9 */
   private void seleccionarTipoSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      // TODO: implement

    HttpSession miSesion = request.getSession(false);
    String codTipoSolic = request.getParameter("btnSelectTipoSolic");

        if ( miSesion != null ) {

            if ( codTipoSolic == null || codTipoSolic.trim().length() == 0 ) {
            response.sendRedirect("controlSolicitud.do?opcion=_verSolicitudes");
            }
        TipoSolicitud miTipoSolic = new TipoSolicitud(Integer.parseInt(codTipoSolic));
        java.util.ArrayList<FormatoTramite> misFormatos = miTipoSolic.getFormatosTramite(FiltroRegistros.ACTIVADO) ;
        request.setAttribute("misFormatos", misFormatos);
        request.setAttribute("miTipoSolic", miTipoSolic);
        request.getRequestDispatcher("formRegistroSolicitud.jsp").forward(request, response);
        }
        else {
        response.sendRedirect("index.jsp");
        }

   }

   /** Permite mostrar el detalle de una solicitud registrada
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @param fileJSP Nombre del archivo JSP en el cual se visualizará el detalle de la solicitud
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid f056a352-a6fc-4e7f-a362-fb14e13e6a68 */
   private void mostrarDetalleSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, String fileJSP) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       HttpSession miSesion = request.getSession(false);
       
       miSesion.setAttribute("urlDetalleSelect", this.getURLActual(request));       

       if ( miSesion != null ) {

       String  strCodSolic = request.getParameter("codSolic");      

       java.util.regex.Pattern miPattern = java.util.regex.Pattern.compile("[0-9]+");
       java.util.regex.Matcher m = miPattern.matcher(strCodSolic);
       int codSolic = 0;
       Solicitud miSolic = null;

       int numPagina = Integer.parseInt( request.getParameter("txtPage") );

            if ( m.find() ) {
            codSolic = Integer.parseInt(strCodSolic);
            miSolic = Solicitud.getSolicitudBD(codSolic);

                if ( miSolic != null ) {
                request.setAttribute("miSolicitud", miSolic);
                request.setAttribute("numPage", numPagina);
                request.setAttribute("urlRetorno", miSesion.getAttribute("urlMenuSelect").toString());
                request.getRequestDispatcher(fileJSP).forward(request, response);
                }

            }

       }

   }

   /** Muestra el formulario que permite dar el visto bueno a la solicitud
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 98eb63aa-b8f4-473b-a06e-61e5791ffa52 */
   private void mostrarFormDarVistoBueno(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
   throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       String codSolic=request.getParameter("codSolic");
       int numPage=Integer.parseInt(request.getParameter("txtPage"));

       if (codSolic != null && codSolic.trim().length()>0){

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){
                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("page", numPage);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString());
                       request.getRequestDispatcher("darVistoBuenoSolic.jsp").forward(request, response);
                }
           }

       }

   }

   /** Muestra el formulario que permitirá dar el visto bueno a la solicitud
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 3b622ff8-d861-47f0-bdc0-4dc5147a1c26 */
   private void mostrarFormAtenderSolic(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

       String codSolic=request.getParameter("codSolic");
       int numPage=Integer.parseInt(request.getParameter("txtPage"));

       if (codSolic != null && codSolic.trim().length()>0){

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){
                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("page", numPage);
                       request.getRequestDispatcher("formAtenderSolic.jsp").forward(request, response);
                }
           }

       }


   }

   /** Muestra el formulario que permite derivar una solicitud
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid bddbb75f-87ab-41bf-bbec-17aba5f1f19b */
   private void mostrarFormDerivarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      
       String codSolic=request.getParameter("codSolic");
       
       if (codSolic != null && codSolic.trim().length()>0){

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){
                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString());
                       request.getRequestDispatcher("formDerivarSolic.jsp").forward(request, response);
                }
           }

       }


   }

      /** Muestra el formulario que permite devolver  la solicitud
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 1344366d-d690-436e-9c99-b7f3f8d41bf2 */
   private void mostrarFormDevolverSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
       // TODO: implement
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);                       
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                       request.getRequestDispatcher("formDevolverSolicitud.jsp").forward(request, response);
                }
           }
       
       }

   }

   /** Muestra el formulario desde donde se va a rechazar la solicitud
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid d46a3d3b-5512-4e06-ac44-fb9d2a2d33e9 */
   private void mostrarFormRechazarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       String codSolic=request.getParameter("codSolic");
       //int numPage=Integer.parseInt(request.getParameter("txtPage"));

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString());
                       request.getRequestDispatcher("formRechazarSolicitud.jsp").forward(request, response);
                }

           }

       }

   }

   /** Permite saber que formulario de detalle se debe mostrar en base a la opcion del menu que se seleccione para la categoria de "Mis requerimientos".
    *
    * En la interfaz grafica del sistema existe un menu el cual tiene dos categorias "Mis requerimientos" y "Mis pendientes".
    *
    *  Esta opcion permite saber a donde dirigirse cuando se de clic en una opcion de este menu.
    *
    * Lo que se mostraría sería un formulario en el cual se detalla la solicitud seleccionada
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @param opcionDetalle
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid b80d26db-878d-45bb-9a0c-6332e3167a3f */
   private void mostrarDetalleSolicRequerimiento(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, String opcionDetalle) throws javax.servlet.ServletException, java.io.IOException {      // TODO: implement

       HttpSession miSesion = request.getSession(false);

       miSesion.setAttribute("urlDetalleSelect", this.getURLActual(request));

       if ( opcionDetalle.trim().equals("_miReqDetalleSolicDarConformidad") ) {
       mostrarDetalleSolicitud(request, response, "detailSolicDarConformidad.jsp" );
       }
       else if ( opcionDetalle.trim().equals("_miReqDetalleSolicDevuelta") ) {
       mostrarDetalleSolicitud(request, response, "detailMiReqDevuelto.jsp" );
       }
       else if ( opcionDetalle.trim().equals("_miReqDetalleSolicRechazada") ) {
       mostrarDetalleSolicitud(request, response, "detailSolicRegistrada.jsp" );
       }
       else if ( opcionDetalle.trim().equals("_miReqDetalleSolicProcAtencion") ) {
       mostrarDetalleSolicitud(request, response, "detailMiReqProcAtencion.jsp" );
       }


   }

   /** Muestra el formulario que permite reenviar una solicitud
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 05451661-afe8-44a1-81eb-842e6c3cf996 */
   private void mostrarFormReenviarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                       request.getRequestDispatcher("formReenviarSolic.jsp").forward(request, response);
                }
           }

       }

       
   }


   /** Muestra el formulario que permite cerrar una solicitud.
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 733f7359-fa41-4c89-8f7b-48a837f7536d */
   private void mostrarFormCerrarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
   // TODO: implement
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                       request.getRequestDispatcher("formCerrarSolic.jsp").forward(request, response);
                }
           }

       }
       
   }

   /** Muestra un formulario que permite devolver una solicitud que ha sido
    * enviada para dar conformidad de usuario.
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 5e2799ef-1edc-4dfb-955f-ab0108f19c96 */
   private void mostrarFormDevolverConformidad(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                       request.getRequestDispatcher("formDevolverConformidad.jsp").forward(request, response);
                }
           }

       }
   }


   /** Muestra el formulario que permite atender una solicitud que ha sido derivada
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 38fa3e05-0599-4d02-bb6e-243ac5522041 */
   private void mostrarFormAtenderSolicDerivada(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                       request.getRequestDispatcher("formAtenderSolicDerivada.jsp").forward(request, response);
                }
           }

       }       
       
   }

   /** Muestra el formulario que permite gestionar las tareas de una solicitud que se encuentra en proceso de atencion
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid f479dd61-f808-439a-bea6-49776d8d157e */
   private void mostrarFormGestionarTareas(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));

                if ( miSolicitud.getUltimoTramite() != null &&
                     miSolicitud.getUltimoTramite().getTipoTramite().getCodigo() == 
                     ValorTipoTramite.ATENDER.getCodigo() ) {

                    if (miSolicitud != null){
                           request.setAttribute("miSolicitud", miSolicitud);
                           request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                           request.getRequestDispatcher("formGestionarTareas.jsp").forward(request, response);
                    }
                    
                }
                
           }

       }

   }

   /** Muestra el formulario que permite finalizar la atencion de una solicitud
    * 
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid c46b6524-3eb7-434c-aec4-3950a854bc74 */
   private void mostrarFormFinalizarAtencion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                       request.getRequestDispatcher("formFinalizarSolicitud.jsp").forward(request, response);
                }
           }

       }

   }

   /** Muestra el formulario que permite enviar la solicitud para recibir conformidad
    *
    * @param request Representa la peticion HTTP
    * @param response Representa la respuesta  HTTP
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 1951af33-14b2-4ed3-9698-9ea725f5c686 */
   private void mostrarFormEnviarConformidad(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                       request.getRequestDispatcher("formEnviarConformidad.jsp").forward(request, response);
                }
           }

       }
   }



    /** Muestra el formulario que permite devolver una solicitud que ha sido finalizada
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid d89aa85d-17a0-489f-a5c1-3c9ba5eee2e1 */
   private void mostrarFormDevolverSolicitudFinalizada(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
       String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                       request.getRequestDispatcher("formDevolverSolicitudFinalizada.jsp").forward(request, response);
                }
           }

       }
   }


   /** @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 10e4fc20-e217-4fee-b38d-64517827c5ae */
   private void mostrarListadoTareas(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

       String codSolic = request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0){

            java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9]+");
            java.util.regex.Matcher m = p.matcher(codSolic);

          if (m.find()){
                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));
                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.getRequestDispatcher("listadoTareas.inc.jsp").forward(request, response);
                }
           }

       }

   }

   
   /** Muestra la pagina que se va a encargar de administrar los mensajes que
    * se envian a los usuarios durante el proceso de atención.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 9553bee2-567c-402c-858e-0fae9e5c4c89 */
   public void mostrarFormGestionarMensajes(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    // TODO: implement
    String codSolic=request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0) {

            Pattern p=Pattern.compile("[0-9]+");
            Matcher m=p.matcher(codSolic);

          if (m.find()){

                Solicitud miSolicitud=Solicitud.getSolicitudBD(Integer.parseInt(codSolic));

                if ( miSolicitud.getUltimoTramite() != null &&
                     miSolicitud.getUltimoTramite().getTipoTramite().getCodigo() ==
                     ValorTipoTramite.ATENDER.getCodigo() ) {

                    if (miSolicitud != null){
                           request.setAttribute("miSolicitud", miSolicitud);
                           request.setAttribute("urlRetorno", request.getSession(false).getAttribute("urlDetalleSelect").toString() );
                           request.getRequestDispatcher("formGestionarMensajes.jsp").forward(request, response);
                    }

                }

           }


        }
    }


   /** @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 0e8a75f9-aa51-4934-919e-d48933792f35 */
   private void mostrarListadoMensajes(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

       String codSolic = request.getParameter("codSolic");

       if (codSolic != null && codSolic.trim().length()>0){

            java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9]+");
            java.util.regex.Matcher m = p.matcher(codSolic);

          if (m.find()){
                Solicitud miSolicitud = Solicitud.getSolicitudBD(Integer.parseInt(codSolic));

                if (miSolicitud != null){
                       request.setAttribute("miSolicitud", miSolicitud);
                       request.getRequestDispatcher("listadoMensajes.inc.jsp").forward(request, response);
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
    * @pdOid ae188e81-d740-4399-ae7c-2e51089c2a3a */
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
    String operacion = request.getParameter("opcion");
    HttpSession miSesion = request.getSession(false); 

        if ( operacion != null && miSesion != null ) {

            if ( operacion.trim().equals("_verSolicitudes") == true )             
            mostrarTiposSolic(request, response);

            if ( operacion.trim().equals("_seleccionarTipoSolicitud") == true )
                seleccionarTipoSolicitud(request, response);

            if ( operacion.trim().equals("_detalleSolicReg") == true )
                mostrarDetalleSolicitud(request, response, "detailSolicRegistrada.jsp");

            if ( operacion.trim().equals("_detalleSolicAtenVB") == true )
                mostrarDetalleSolicitud(request, response, "detailSolicDarVistoBueno.jsp");

            if ( operacion.trim().equals("_verFormDarVB") == true )
                mostrarFormDarVistoBueno(request, response);

            if ( operacion.trim().equals("_detalleSolicPorAtender") == true )
                mostrarDetalleSolicitud(request, response, "detailSolicPorAtender.jsp");

            if ( operacion.trim().equals("_verFormAtenderSolic") == true )
                mostrarFormAtenderSolic(request, response);

            if ( operacion.trim().equals("_verFormDerivarSolic") == true )
                mostrarFormDerivarSolicitud(request, response);

            if ( operacion.trim().equals("_verFormDevolverSolic") == true )
                mostrarFormDevolverSolicitud(request, response);

            if ( operacion.trim().equals("_verFormRechazarSolic") == true )
                mostrarFormRechazarSolicitud(request, response);

            if ( operacion.trim().equals("_verFormReenviarSolicitud") )
                mostrarFormReenviarSolicitud(request, response);

            if ( operacion.trim().equals("_miReqDetalleSolicDarConformidad") == true || 
                 operacion.trim().equals("_miReqDetalleSolicDevuelta") == true ||
                 operacion.trim().equals("_miReqDetalleSolicRechazada") == true ||
                 operacion.trim().equals("_miReqDetalleSolicProcAtencion") == true )
                mostrarDetalleSolicRequerimiento(request, response, operacion);

            if ( operacion.trim().equals("_verFormCerrarSolic") )
                mostrarFormCerrarSolicitud(request, response);

            if ( operacion.trim().equals("_verFormDevolverConformidad") )
                mostrarFormDevolverConformidad(request, response);

            if ( operacion.trim().equals("_detalleSolicPendDerivada") )
                mostrarDetalleSolicitud(request, response, "detalleSolicPendDerivada.jsp");

            if ( operacion.trim().equals("_verFormAtenderSolicDerivada") )
                mostrarFormAtenderSolicDerivada(request, response);

            if ( operacion.trim().equals("_detalleSolicPendProcAtencion") )
                mostrarDetalleSolicitud(request, response, "detailSolicProcAtencion.jsp");

            if ( operacion.trim().equals("_verFormGestionarTareas") )
                mostrarFormGestionarTareas(request, response);
            //_verFormGestionarMensajes

            if ( operacion.trim().equals("_verFormGestionarMensajes") )
                mostrarFormGestionarMensajes(request, response);

            if ( operacion.trim().equals("_verFormFinalizarAtencion") )
                mostrarFormFinalizarAtencion(request, response);

            if ( operacion.trim().equals("_detalleSolicPendEnvConformidad") )
                mostrarDetalleSolicitud(request, response, "detailSolicEnviarConformidad.jsp");

            if ( operacion.trim().equals("_verFormEnviarConformidad") )
                mostrarFormEnviarConformidad(request, response);

            if ( operacion.trim().equals("_verFormDevolverSolicitudFinalizada") )
                mostrarFormDevolverSolicitudFinalizada(request, response);

            if ( operacion.trim().equals("_mostrarListadoTareas") )
                    mostrarListadoTareas(request, response);

            if ( operacion.trim().equals("_mostrarListadoMensajes") )
                    mostrarListadoMensajes(request, response);
            

        }
    
   }

   /** Called by the server (via the service method) to allow a servlet to handle a POST request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 494db2c3-6e01-48d1-b74c-85bc55145c0b */
   @Override
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement


    HttpSession miSesion = request.getSession(false); 
    String opcion = request.getParameter("opcion");

        if ( opcion != null && miSesion != null ) {

            if ( opcion.trim().equals("_registrarSolicitud") == true )
                    this.registrarSolicitud(request, response);

        }

   }

   private String getURLActual(javax.servlet.http.HttpServletRequest request) {

    String reqUrl = request.getRequestURL().toString();
    String queryString = request.getQueryString();

    if (queryString != null) {
	reqUrl += "?"+queryString;
    }

    return reqUrl;

   }

}