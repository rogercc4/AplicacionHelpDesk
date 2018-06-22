/***********************************************************************
 * Module:  ControlTrabajador.java
 * Author:  rcontreras
 * Purpose: Defines the Class ControlTrabajador
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
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.FileUpload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import helpdesk.model.pattern.prototype.FactoryPrototypeBuscador;
import helpdesk.model.pattern.prototype.TipoDeBusqueda;

/** @pdOid d756a27e-160a-4427-a95f-e0a6ccbbef3e */
public class ControlTrabajador extends javax.servlet.http.HttpServlet {
   /** Permite hacer el cálculo de las solicitudes que han recien generadas
    * por un trabajador
    *
    * @param request
    * @param response
    * @pdOid 514c02bb-26bf-422d-b8f4-1aa8ef74d2c1 */
   private void mostrarSolicRegistradas(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
           throws ServletException, IOException {
      // TODO: implement

     String valPagina = request.getParameter("txtPage");
     HttpSession miSesion = request.getSession(false);
     
      miSesion.setAttribute("urlMenuSelect", this.getURLActual(request));
     
     int numPage = 1; 

        if ( valPagina != null )
            numPage = Integer.parseInt(valPagina);

    int tamanioPagina = Integer.parseInt(request.getServletContext().getInitParameter("tamanioPagina"));
    
    Trabajador miTrabajador = (Trabajador)miSesion.getAttribute("sTrabajador");
    Cargo miCargo = (Cargo)miSesion.getAttribute("sCargo");

    Buscador miBusq = new Buscador();
    miBusq.setPagina(numPage);
    miBusq.setTamanioPagina(tamanioPagina);

    ArrayList<Solicitud> misSolicitudes = miBusq.getSolicRegistradas(miTrabajador, miCargo);

    request.setAttribute("misSolicitudes", misSolicitudes);
    request.setAttribute("miBuscador", miBusq);
    request.setAttribute("miUrlDetail", "controlSolicitud.do?opcion=_detalleSolicReg");
    request.setAttribute("miOpcion", "_verSolicReg");
    request.setAttribute("tituloPagina", "Solicitudes registradas");
    
    
    request.getRequestDispatcher("listadoSolicitudes.jsp").forward(request, response);

   }

   /** Permite mostrar aquellas solicitudes a las cuales se les
    * debe atender para darle el visto bueno
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid 202d2ee1-991d-43d4-8b17-d11d66bdf193 */
   private void mostrarSolicAtenderVB(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      // TODO: implement
      
     String valPagina = request.getParameter("txtPage");
     HttpSession miSesion = request.getSession(false);     
     miSesion.setAttribute("urlMenuSelect", this.getURLActual(request));
     
     int numPage = 1;

        if ( valPagina != null )
            numPage = Integer.parseInt(valPagina);

    int tamanioPagina = Integer.parseInt(request.getServletContext().getInitParameter("tamanioPagina"));

    Trabajador miTrabajador = (Trabajador)miSesion.getAttribute("sTrabajador");
    Cargo miCargo = (Cargo)miSesion.getAttribute("sCargo");

    Buscador miBusq = new Buscador();

    miBusq.setPagina(numPage);
    miBusq.setTamanioPagina(tamanioPagina);
    ArrayList<Solicitud> misSolicitudes = miBusq.getSolicPendVistoBueno(miTrabajador, miCargo);

    request.setAttribute("misSolicitudes", misSolicitudes);
    request.setAttribute("miBuscador", miBusq);
    request.setAttribute("miUrlDetail", "controlSolicitud.do?opcion=_detalleSolicAtenVB");
    request.setAttribute("miOpcion", "_verSolicAtenVB");
    request.setAttribute("tituloPagina", "Solicitudes para dar Visto Bueno");

    request.getRequestDispatcher("listadoSolicitudes.jsp").forward(request, response);

   }

   /** Permite dar visto bueno a una solicitud
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid 250c8dbc-d32e-4041-8b7e-d02d669415d2 */
   private void darVistoBueno(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
   throws ServletException, IOException {
        try {
            // TODO: implement
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo =(Cargo)miSesion.getAttribute("sCargo");

            JefeArea miJefe = new JefeArea(miTrabajador.getUsuario(),miCargo);
            int codSolic = Integer.parseInt(request.getParameter("hdCodSolic").trim());            
            String strDetalle=request.getParameter("txtDetalle");

                if ( codSolic > 0 ) {
                    Solicitud miSolicitud=Solicitud.getSolicitudBD(codSolic);

                    if (miJefe.darVistoBueno(miSolicitud,strDetalle)){
                        request.setAttribute("msgSistema", "Se proceso el Visto Bueno a la solicitud");
                        request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
                        request.setAttribute("msgTarget", "_self");
                        request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
                    }

                }

            } catch (HelpDeskException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        }

   }

    /** Muestra aquellas solicitudes que ya pueden ser atendidas
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid 81aa0517-f041-4529-bee5-af2221d1b12f */
   private void mostrarSolicPorAtender(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      // TODO: implement
     String valPagina = request.getParameter("txtPage");
     HttpSession miSesion = request.getSession(false);

      miSesion.setAttribute("urlMenuSelect", this.getURLActual(request));

     int numPage = 1;

        if ( valPagina != null )
            numPage = Integer.parseInt(valPagina);

    int tamanioPagina = Integer.parseInt(request.getServletContext().getInitParameter("tamanioPagina"));

    Trabajador miTrabajador = (Trabajador)miSesion.getAttribute("sTrabajador");
    Cargo miCargo = (Cargo)miSesion.getAttribute("sCargo");

    Buscador miBusq = new Buscador();

    miBusq.setPagina(numPage);
    miBusq.setTamanioPagina(tamanioPagina);
    ArrayList<Solicitud> misSolicitudes = miBusq.getSolicPendParaAtender(miTrabajador, miCargo);

    request.setAttribute("misSolicitudes", misSolicitudes);
    request.setAttribute("miBuscador", miBusq);
    request.setAttribute("miUrlDetail", "controlSolicitud.do?opcion=_detalleSolicPorAtender");
    request.setAttribute("miOpcion", "_verSolicPorAtender");
    request.setAttribute("tituloPagina", "Para Atender ( Lista Solicitudes )");

    request.getRequestDispatcher("listadoSolicitudes.jsp").forward(request, response);
    
   }


   /** Permite dar atención a una solicitud
    * 
    * @param request 
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid ac730c5e-9a12-4fac-95b5-540bebd50a63 */
   private void atenderSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    throws ServletException, IOException {
    // TODO: implement
        try {
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");            

            int codSubCateg =0, codPrioridad=0, codSolicitud=0 ; 

            codSubCateg = Integer.parseInt(request.getParameter("selectSubCategoria").trim());
            codPrioridad = Integer.parseInt(request.getParameter("selectPrioridad").trim());
            codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim()); 

            if ( codSolicitud > 0 && codSubCateg > 0 && codPrioridad > 0 ) { 

            Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud);
            SubCategoria miSubCateg = SubCategoria.getSubCategoriaBD(codSubCateg);
            Prioridad miPrioridad = Prioridad.getPrioridadBD(codPrioridad);

            TrabajadorInformatica miTrabInfor = new TrabajadorInformatica(miTrabajador.getUsuario(), miCargo);
            miTrabInfor.atenderSolicitud(miSolicitud, miSubCateg, miPrioridad);

            request.setAttribute("msgSistema", "Se procedio a atender la solicitud");
            request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
            
            }
        }
        catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", "controlTrabajador.do?opcion=_verSolicPorAtender");
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
            
            }
        }    

      /** Se encarga de derivar una solicitud a una persona en especifico
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid a4cf3650-64fb-45b2-80d7-52bcbb06b44b */
   private void derivarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      // TODO: implement      

     try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");
            TramiteArchivo miArchivo = null ;
            
            int codSubCateg =0, codPrioridad=0, codSolicitud=0, codCargoDest=0;
            String detalle = null, usuarioDestino = null, nombreArchivo = null ;

            if ( isMultipart == true  ) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();            

                while ( iter.hasNext() ) {
                FileItem item = (FileItem) iter.next();

                    if (item.isFormField() == true) {

                        if ( item.getFieldName().trim().equals("selectSubCategoria") ) {
                        codSubCateg = Integer.parseInt(item.getString().trim());
                        }
                        else if ( item.getFieldName().trim().equals("selectPrioridad") ) {
                        codPrioridad = Integer.parseInt(item.getString().trim());
                        }
                        else if ( item.getFieldName().trim().equals("hdCodSolic") ) {
                        codSolicitud = Integer.parseInt(item.getString().trim());
                        }
                        else if ( item.getFieldName().trim().equals("txtNombreArchivo") ) {
                        nombreArchivo = item.getString() ; 
                        }
                        else if ( item.getFieldName().trim().equals("txtDetalle") ) {
                        detalle = item.getString(); 
                        }
                        else if ( item.getFieldName().trim().equals("selectCargo") ) {
                        codCargoDest = Integer.parseInt(item.getString().trim()); 
                        }
                        else if ( item.getFieldName().trim().equals("selectPersona") ) {
                        usuarioDestino = item.getString(); 
                        }


                    }
                    else {

                        if ( item.getSize() > 0 ) {
                        GestorFichero gf = new GestorFichero();
                        gf.guardarFicheroDisco(item, request);
                        miArchivo = new TramiteArchivo();
                        miArchivo.setNombre(nombreArchivo);
                        miArchivo.setArchivo( gf.getNombre());
                        miArchivo.setTipoContenido( gf.getTipoContenido());
                        }
                    
                    }

                }

            }
            else {
            codSubCateg = Integer.parseInt(request.getParameter("selectSubCategoria").trim());
            codPrioridad = Integer.parseInt(request.getParameter("selectPrioridad").trim());
            codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim());            
            detalle = request.getParameter("txtDetalle").trim();
            codCargoDest = Integer.parseInt(request.getParameter("selectCargo").trim());
            usuarioDestino = request.getParameter("selectPersona").trim();
            }

            if ( codSolicitud > 0 && codSubCateg > 0 && codPrioridad > 0 ) {

            Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud); 
            SubCategoria miSubCateg = SubCategoria.getSubCategoriaBD(codSubCateg);
            Prioridad miPrioridad = Prioridad.getPrioridadBD(codPrioridad);
            TrabajadorInformatica miTrabInfor = new TrabajadorInformatica(miTrabajador.getUsuario(), miCargo);

            miTrabInfor.clasificarSolicitud(miSolicitud, miSubCateg, miPrioridad);
            
            Tramite miTramite = new Tramite();
            miTramite.setUsuarioDestino(usuarioDestino);
            miTramite.setCodCargoDestino(codCargoDest);
            miTramite.setDetalle(detalle);
            miTramite.setCodSolicitud(codSolicitud);
            
            miTrabInfor.derivarSolicitud(miTramite, miArchivo);

            request.setAttribute("msgSistema", "La solicitud fue derivada correctamente");
            request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
            request.setAttribute("msgTarget", "_self"); 
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
            
            }
            
        } catch (FileUploadException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        } catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

            }

   }


   /** Permite devolver una solicitud a la persona que lo ha enviado.
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid c3893578-640b-4012-b515-23020150d375 */
   private void devolverSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      // TODO: implement

     try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");
            TramiteArchivo miArchivo = null ;

            int codSolicitud=0;
            String detalle = null, nombreArchivo = null ;

            if ( isMultipart == true  ) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

                while ( iter.hasNext() ) {
                FileItem item = (FileItem) iter.next();

                    if (item.isFormField() == true) {

                        if ( item.getFieldName().trim().equals("txtDetalle") ) {
                        detalle = item.getString().trim();
                        }
                        else if ( item.getFieldName().trim().equals("txtNombreArchivo") ) {
                        nombreArchivo = item.getString() ;
                        }
                        else if ( item.getFieldName().trim().equals("hdCodSolic") ) {
                        codSolicitud = Integer.parseInt(item.getString().trim());
                        }

                    }
                    else {

                        if ( item.getSize() > 0 ) {
                        GestorFichero gf = new GestorFichero();
                        gf.guardarFicheroDisco(item, request);
                        miArchivo = new TramiteArchivo();
                        miArchivo.setNombre(nombreArchivo);
                        miArchivo.setArchivo( gf.getNombre());
                        miArchivo.setTipoContenido( gf.getTipoContenido());
                        }

                    }

                }

            }
            else {            
            codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim());
            detalle = request.getParameter("txtDetalle").trim();                        
            }

            if ( codSolicitud > 0 ) {

            Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud);
            int codUltimoTipoTramite = miSolicitud.getUltimoTramite().getCodTipoTramite();

            miTrabajador.devolverSolicitud(miSolicitud, miCargo, detalle, miArchivo);

            request.setAttribute("msgSistema", "La solicitud ha sido devuelta correctamente");

                /*if ( codUltimoTipoTramite == ValorTipoTramite.ENVIO_VISTO_BUENO.getCodigo() ) {*/
                request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
                /*}
                else {
                request.setAttribute("msgurl", "controlTrabajador.do?opcion=_verSolicAtenVB");
                }*/
            
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

            }
            else {
            throw new HelpDeskException("La solicitud que se desea devolver no existe");
            }

        } catch (FileUploadException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", "Hubo un problema al subir el archivo");
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        } catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

         }        

   }

    /** Permite rechazar una solicitud
    *
    * @param request
    * @param response
    * @exception ServletException
    * @exception IOException
    * @pdOid d9078a44-580d-467d-8997-1c8447676e8d */
   private void rechazarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
   // TODO: implement
     try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");
            TramiteArchivo miArchivo = null ;

            int codSolicitud=0;
            String detalle = null, nombreArchivo = null ;

            if ( isMultipart == true  ) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

                while ( iter.hasNext() ) {
                FileItem item = (FileItem) iter.next();

                    if (item.isFormField() == true) {

                        if ( item.getFieldName().trim().equals("txtDetalle") ) {
                        detalle = item.getString().trim();
                        }
                        else if ( item.getFieldName().trim().equals("txtNombreArchivo") ) {
                        nombreArchivo = item.getString() ;
                        }
                        else if ( item.getFieldName().trim().equals("hdCodSolic") ) {
                        codSolicitud = Integer.parseInt(item.getString().trim());
                        }

                    }
                    else {

                        if ( item.getSize() > 0 ) {
                        GestorFichero gf = new GestorFichero();
                        gf.guardarFicheroDisco(item, request);
                        miArchivo = new TramiteArchivo();
                        miArchivo.setNombre(nombreArchivo);
                        miArchivo.setArchivo( gf.getNombre());
                        miArchivo.setTipoContenido( gf.getTipoContenido());
                        }

                    }

                }

            }
            else {
            codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim());
            detalle = request.getParameter("txtDetalle").trim();
            }

            if ( codSolicitud > 0 ) {

            Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud);
            int codUltimoTipoTramite = miSolicitud.getUltimoTramite().getCodTipoTramite();

            miTrabajador.rechazarSolicitud(miSolicitud, miCargo, detalle, miArchivo);

            request.setAttribute("msgSistema", "La solicitud ha sido rechazada correctamente");
                /*
                if ( codUltimoTipoTramite == ValorTipoTramite.ENVIO_VISTO_BUENO.getCodigo() ||
                codUltimoTipoTramite == ValorTipoTramite.ENVIO_ATENCION.getCodigo()) {
                request.setAttribute("msgurl", "controlTrabajador.do?opcion=_verSolicAtenVB");
                }
                else {
                request.setAttribute("msgurl", "controlTrabajador.do?opcion=_verSolicAtenVB");
                }
                */
            request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());

            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

            }
            else {
            throw new HelpDeskException("La solicitud que se desea devolver no existe");
            }

        } catch (FileUploadException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", "Hubo un problema al subir el archivo");
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        } catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

         }
   
   }

   /** Permite generar la información que sera de utilidad para mostrar el listado de solicitudes. Este listado se muestra de acuerdo a la opcion del menu que se seleccione.
    *
    * @param request
    * @param response
    * @param opcionMenu Opcion del menu seleccionada. Es de utilidad para filtrar las solicitudes que deben mostrarse en el listado
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid fd6eb747-c9ee-4de9-aaad-9c4e52ce8ef5 */
   private void mostrarMisRequerimientos(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, String opcionMenu) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
     String valPagina = request.getParameter("txtPage");
     HttpSession miSesion = request.getSession(false);  
     
     miSesion.setAttribute("urlMenuSelect", this.getURLActual(request));

     int numPage = 1;

        if ( valPagina != null )
            numPage = Integer.parseInt(valPagina);

    int tamanioPagina = Integer.parseInt(request.getServletContext().getInitParameter("tamanioPagina"));

    Trabajador miTrabajador = (Trabajador)miSesion.getAttribute("sTrabajador");
    Cargo miCargo = (Cargo)miSesion.getAttribute("sCargo");

    Buscador miBusq = new Buscador();

    miBusq.setPagina(numPage);
    miBusq.setTamanioPagina(tamanioPagina);
    
    ArrayList<Solicitud> misSolicitudes = null;
    String urlDetail = null;
    String tituloPagina = null ; 

    if ( opcionMenu.trim().equals("_misReqVerSolicProcAtencion") )    { 
    misSolicitudes = miBusq.getMisReqProcesoAtencion(miTrabajador, miCargo); 
    urlDetail = "controlSolicitud.do?opcion=_miReqDetalleSolicProcAtencion";    
    tituloPagina = "En proceso de atencion ( Lista de Solicitudes )";
    }
    else if ( opcionMenu.trim().equals("_misReqVerSolicAsignadas") )    {
    misSolicitudes = miBusq.getMisReqAsignados(miTrabajador, miCargo);
    urlDetail = "controlSolicitud.do?opcion=_detalleSolicReg";
    tituloPagina = "Mis solicitudes que han sido Asignadas";
    }
    else if ( opcionMenu.trim().equals("_misReqVerSolicDarConformidad") )    {
    misSolicitudes = miBusq.getMisReqParaConformidad(miTrabajador, miCargo);
    urlDetail = "controlSolicitud.do?opcion=_miReqDetalleSolicDarConformidad";
    tituloPagina = "Solicitudes para dar conformidad";
    }
    else if ( opcionMenu.trim().equals("_misReqVerSolicDevueltas") )  {
    misSolicitudes = miBusq.getMisReqDevueltos(miTrabajador, miCargo);
    urlDetail = "controlSolicitud.do?opcion=_miReqDetalleSolicDevuelta";
    tituloPagina = "Solicitudes que han sido devueltas";
    }
    else if ( opcionMenu.trim().equals("_misReqVerSolicRechazadas") )  {
    misSolicitudes = miBusq.getMisReqRechazados(miTrabajador, miCargo);
    urlDetail = "controlSolicitud.do?opcion=_miReqDetalleSolicRechazada";
    tituloPagina = "Solicitudes que han sido rechazadas";
    }
    
    request.setAttribute("misSolicitudes", misSolicitudes);
    request.setAttribute("miBuscador", miBusq);
    request.setAttribute("miUrlDetail", urlDetail);    
    request.setAttribute("tituloPagina", tituloPagina);

    request.getRequestDispatcher("listadoSolicitudes.jsp").forward(request, response); 
    
    }


   /** Permite el reenvio de una solicitud
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 39567a2e-5fd4-475b-b4dd-fcb6d9ed51d9 */
   private void reenviarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) 
   throws javax.servlet.ServletException, java.io.IOException{
      // TODO: implement

     try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");

            TramiteArchivo miArchivo = null ;

            int codSolicitud=0;
            String detalle = null, nombreArchivo = null ;

            if ( isMultipart == true  ) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();

                while ( iter.hasNext() ) {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField() == true) {

                        if ( item.getFieldName().trim().equals("txtDetalle") ) {
                            detalle = item.getString().trim();
                        }
                        else if ( item.getFieldName().trim().equals("txtNombreArchivo") ) {
                            nombreArchivo = item.getString() ;
                        }
                        else if ( item.getFieldName().trim().equals("hdCodSolic") ) {
                            codSolicitud = Integer.parseInt(item.getString().trim());
                        }

                    }
                    else {

                        if ( item.getSize() > 0 ) {
                            GestorFichero gf = new GestorFichero();
                            gf.guardarFicheroDisco(item, request);
                            miArchivo = new TramiteArchivo();
                            miArchivo.setNombre(nombreArchivo);
                            miArchivo.setArchivo( gf.getNombre());
                            miArchivo.setTipoContenido( gf.getTipoContenido());
                        }

                    }

                }

            }
            else {
                codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim());
                detalle = request.getParameter("txtDetalle").trim();
            }

            if ( codSolicitud > 0 ) {

                Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud);

                miTrabajador.reenviarSolicitud(miSolicitud, detalle, miArchivo);

                request.setAttribute("msgSistema", "La solicitud ha sido reenviada correctamente");
                request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
                request.setAttribute("msgTarget", "_self");
                request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
            }
            else {
                throw new HelpDeskException("La solicitud que se desea reenviar no existe");
            }

        } catch (FileUploadException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", "Hubo un problema al subir el archivo");
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        } catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

         }
   }


    /** Permite cerrar una solicitud.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 7a455442-bc30-4d54-9495-5297fe7f2aa4 */
   private void cerrarSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
   // TODO: implement
         try {
            // TODO: implement
            HttpSession miSesion = request.getSession(false);
            
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");            
            
            int codSolic = Integer.parseInt(request.getParameter("hdCodSolic").trim());

            String strDetalle = request.getParameter("txtDetalle");

                if ( codSolic > 0 ){
                    Solicitud miSolicitud=Solicitud.getSolicitudBD(codSolic);
                    miTrabajador.cerrarSolicitud(miSolicitud, strDetalle);
                    request.setAttribute("msgSistema", "La solicitud fue cerrada correctamente");
                    request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
                    request.setAttribute("msgTarget", "_self");
                    request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
                }

            } catch (HelpDeskException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        }

   }

   /** Permite devolver una solicitud que ha sido enviada para dar conformidad de usuario
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 1500b30a-6983-4ebf-b797-3adf0566a7f5 */
   private void devolverSolicConformidad(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
     try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");
            TramiteArchivo miArchivo = null ;

            int codSolicitud=0;
            String detalle = null, nombreArchivo = null ;

            if ( isMultipart == true  ) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

                while ( iter.hasNext() ) {
                FileItem item = (FileItem) iter.next();

                    if (item.isFormField() == true) {

                        if ( item.getFieldName().trim().equals("txtDetalle") ) {
                        detalle = item.getString().trim();
                        }
                        else if ( item.getFieldName().trim().equals("txtNombreArchivo") ) {
                        nombreArchivo = item.getString() ;
                        }
                        else if ( item.getFieldName().trim().equals("hdCodSolic") ) {
                        codSolicitud = Integer.parseInt(item.getString().trim());
                        }

                    }
                    else {

                        if ( item.getSize() > 0 ) {
                        GestorFichero gf = new GestorFichero();
                        gf.guardarFicheroDisco(item, request);
                        miArchivo = new TramiteArchivo();
                        miArchivo.setNombre(nombreArchivo);
                        miArchivo.setArchivo( gf.getNombre());
                        miArchivo.setTipoContenido( gf.getTipoContenido());
                        }

                    }

                }

            }
            else {
            codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim());
            detalle = request.getParameter("txtDetalle").trim();
            }

            if ( codSolicitud > 0 ) {

            Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud);
            miTrabajador.devolverConformidad(miSolicitud, detalle, miArchivo);

            request.setAttribute("msgSistema", "La solicitud ha sido devuelta correctamente");
            request.setAttribute("msgTarget", "_self");
            request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

            }
            else {
            throw new HelpDeskException("La solicitud que se desea devolver no existe");
            }

        } catch (FileUploadException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", "Hubo un problema al subir el archivo");
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        } catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

         }

   }

      /** Muestra las solicitudes que se encuentran pendientes para realizar alguna atencion.
    *
    * Por ejemplo: Para visualizar solicitudes que estan pendientes de visto bueno, para atender,
    * aquellas que han sido derivadas, etc.
    *
    * @param request
    * @param response
    * @param opcionMenu Opcion del menu seleccionada. Es de utilidad para filtrar las solicitudes que deben mostrarse en el listado
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid a693c7f9-4825-40c9-b9d0-40fb02e68a62 */
   private void mostrarMisPendientes(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, String opcionMenu) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

      HttpSession miSesion = request.getSession(false);
      
      miSesion.setAttribute("urlMenuSelect", this.getURLActual(request));

      Trabajador miTrabajador = (Trabajador)miSesion.getAttribute("sTrabajador");
      Cargo miCargo = (Cargo)miSesion.getAttribute("sCargo");

      ArrayList<Solicitud> misSolicitudes = new ArrayList<Solicitud>();
      Buscador miBusq = new Buscador();
      String urlDetail = null;
      String tituloPagina = null ;

      if ( opcionMenu.trim().equals("_verSolicDerivadas") ) {
      misSolicitudes = miBusq.getSolicPendDerivadas(miTrabajador, miCargo);
      urlDetail = "controlSolicitud.do?opcion=_detalleSolicPendDerivada";
      tituloPagina = "Solicitudes derivadas";
      }
      else if ( opcionMenu.trim().equals("_verSolicProcAtencion") ) {
      misSolicitudes = miBusq.getSolicPendProcAtencion(miTrabajador, miCargo);
      urlDetail = "controlSolicitud.do?opcion=_detalleSolicPendProcAtencion";
      tituloPagina = "Solicitudes en proceso de atencion";
      }
      else if ( opcionMenu.trim().equals("_verSolicEnvConformidad") ) {
      misSolicitudes = miBusq.getSolicPendEnvConformidad(miTrabajador, miCargo);
      urlDetail = "controlSolicitud.do?opcion=_detalleSolicPendEnvConformidad";
      tituloPagina = "Solicitudes para pedir conformidad";
      }
    
    request.setAttribute("misSolicitudes", misSolicitudes);
    request.setAttribute("miBuscador", miBusq);
    request.setAttribute("miUrlDetail", urlDetail);    
    request.setAttribute("tituloPagina", tituloPagina);
    
    request.getRequestDispatcher("listadoSolicitudes.jsp").forward(request, response);

   }


   /** Permite atender una solicitud que ha sido derivada.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid ac3b2c74-ff2f-46bd-a623-988654f5d510 */
   private void atenderSolicitudDerivada(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
        try {
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");

            int codSubCateg =0, codPrioridad=0, codSolicitud=0 ;

            codSubCateg = Integer.parseInt(request.getParameter("selectSubCategoria").trim());
            codPrioridad = Integer.parseInt(request.getParameter("hdPrioridad").trim());
            codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim());

            if ( codSolicitud > 0 && codSubCateg > 0 && codPrioridad > 0 ) {

            Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud);
            SubCategoria miSubCateg = SubCategoria.getSubCategoriaBD(codSubCateg);
            Prioridad miPrioridad = Prioridad.getPrioridadBD(codPrioridad);

            TrabajadorInformatica miTrabInfor = new TrabajadorInformatica(miTrabajador.getUsuario(), miCargo);
            miTrabInfor.atenderSolicitud(miSolicitud, miSubCateg, miPrioridad);

            request.setAttribute("msgSistema", "Se procedio a atender la solicitud");
            request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

            }
        }
        catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

       }

   }

   /** Finalizar atención de la solicitud
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid c33ffa4c-4bbc-425a-a18e-fd929a7b7d9f */
   private void finalizarAtencionSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
        try {
            // TODO: implement
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo =(Cargo)miSesion.getAttribute("sCargo");

            TrabajadorInformatica trabSelect = new TrabajadorInformatica(miTrabajador.getUsuario(),miCargo);
            int codSolic = Integer.parseInt(request.getParameter("hdCodSolic").trim());
            String strDetalle = request.getParameter("txtDetalle");

                if ( codSolic > 0 ) {
                    Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolic);

                    trabSelect.finalizarAtencion(miSolicitud, strDetalle);

                    request.setAttribute("msgSistema", "Se finalizo la atencion de la solicitud ...");
                    request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
                    request.setAttribute("msgTarget", "_self");
                    request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
                }

            } catch (HelpDeskException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        }

   }


    /** Envia la solicitud para recibir la conformidad del usuario
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid dfa3d63a-84b6-4aee-81c2-200ad5f6e8fc */
   private void enviarParaConformidadSolicitud(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

        try {
            // TODO: implement
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo =(Cargo)miSesion.getAttribute("sCargo");

            TrabajadorInformatica trabSelect = new TrabajadorInformatica(miTrabajador.getUsuario(),miCargo);
            int codSolic = Integer.parseInt(request.getParameter("hdCodSolic").trim());
            String strDetalle = request.getParameter("txtDetalle");

                if ( codSolic > 0 ) {
                    Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolic);

                    trabSelect.enviarParaConformidad(miSolicitud, strDetalle);

                    request.setAttribute("msgSistema", "La solicitud fue correctamente enviada para la conformidad del usuario ...");
                    request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
                    request.setAttribute("msgTarget", "_self");
                    request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
                    
                }

            } catch (HelpDeskException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        }

   }

   /** Permite devolver una solicitud que ha sido finalizada
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 083bd522-08e7-495a-8106-201dd035011f */
   private void devolverSolicitudFinalizada(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
     try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");
            TramiteArchivo miArchivo = null ;

            TrabajadorInformatica trabInf = new  TrabajadorInformatica(miTrabajador.getUsuario(), miCargo);

            int codSolicitud=0;
            String detalle = null, nombreArchivo = null ;

            if ( isMultipart == true  ) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

                while ( iter.hasNext() ) {
                FileItem item = (FileItem) iter.next();

                    if (item.isFormField() == true) {

                        if ( item.getFieldName().trim().equals("txtDetalle") ) {
                        detalle = item.getString().trim();
                        }
                        else if ( item.getFieldName().trim().equals("txtNombreArchivo") ) {
                        nombreArchivo = item.getString() ;
                        }
                        else if ( item.getFieldName().trim().equals("hdCodSolic") ) {
                        codSolicitud = Integer.parseInt(item.getString().trim());
                        }

                    }
                    else {

                        if ( item.getSize() > 0 ) {
                        GestorFichero gf = new GestorFichero();
                        gf.guardarFicheroDisco(item, request);
                        miArchivo = new TramiteArchivo();
                        miArchivo.setNombre(nombreArchivo);
                        miArchivo.setArchivo( gf.getNombre());
                        miArchivo.setTipoContenido( gf.getTipoContenido());
                        }

                    }

                }

            }
            else {
            codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim());
            detalle = request.getParameter("txtDetalle").trim();
            }

            if ( codSolicitud > 0 ) {

            Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud);
            trabInf.devolverSolicitudFinalizada(miSolicitud, detalle, miArchivo);

            request.setAttribute("msgSistema", "La solicitud ha sido devuelta correctamente");
            request.setAttribute("msgTarget", "_self");
            request.setAttribute("msgurl", miSesion.getAttribute("urlMenuSelect").toString());
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

            }
            else {
            throw new HelpDeskException("La solicitud que se desea devolver no existe");
            }

        } catch (FileUploadException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", "Hubo un problema al subir el archivo");
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);
        } catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msgSistema", ex.getMessage());
            request.setAttribute("msgurl", request.getHeader("Referer"));
            request.setAttribute("msgTarget", "_self");
            request.getRequestDispatcher("mensajeSistema.jsp").forward(request, response);

         }

   }


   private void registrarTarea(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

        try {

            HttpSession miSesion = request.getSession(false);

            String codigo = request.getParameter("hdCodSolic");
            String descripcion = request.getParameter("txtDescripcion");
            String valFechaIni = request.getParameter("txtFechaInicio");
            String valFechaFin = request.getParameter("txtFechaFin");

            if ( request.getCharacterEncoding() == null )
                    descripcion = new String(descripcion.getBytes(), "utf-8");
            else if(request.getCharacterEncoding().trim().toLowerCase().equals("utf-8") == false)
                    descripcion = new String(descripcion.getBytes(), "utf-8");

            Locale loc = new Locale("es", "PE");
            
            java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.MEDIUM,
                                                                            java.text.DateFormat.SHORT, loc);
            
            df.setLenient(false); 

            java.util.Date fechaIni = df.parse(valFechaIni);
            java.util.Date fechaFin = df.parse(valFechaFin);

            int codSolic = Integer.parseInt(codigo.trim());
            Solicitud solicitud = Solicitud.getSolicitudBD(codSolic);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");
            TrabajadorInformatica trabInf = new TrabajadorInformatica(miTrabajador.getUsuario(), miCargo);
            trabInf.registrarTarea(solicitud, descripcion, fechaIni, fechaFin);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "true");
            parametros.put("MENSAJE_OPERACION", "La tarea se registro correctamente");

            this.generarRespuestaXMLOperacion(response, parametros);

        } catch (ParseException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", "Formato incorrecto en las fechas");

            this.generarRespuestaXMLOperacion(response, parametros);

        } catch (HelpDeskException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", ex.getMessage());

            this.generarRespuestaXMLOperacion(response, parametros);
        }
        catch( Exception ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", "No pudo registrarse la tarea");

            this.generarRespuestaXMLOperacion(response, parametros);
        }

   }

   /** Permite eliminar una tarea
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid a52fb642-9b87-4ca4-9b12-7126820e2fdc */
   private void eliminarTarea(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement
      String codigo = request.getParameter("codTarea");

      try {

          if ( codigo != null ) {

              int codTarea = Integer.parseInt(codigo.trim());

              HttpSession sesion = request.getSession(false);
              Trabajador miTrab = (Trabajador) sesion.getAttribute("sTrabajador");
              Cargo miCargo = (Cargo) sesion.getAttribute("sCargo");

              TrabajadorInformatica trabInf = new TrabajadorInformatica(miTrab.getUsuario(), miCargo);

              trabInf.eliminarTarea( Tarea.getTareaBD(codTarea) );

              HashMap parametros = new HashMap();
              parametros.put("OPERACION_CORRECTA", "true");
              parametros.put("MENSAJE_OPERACION", "La tarea fue eliminada correctamente");
              
              this.generarRespuestaXMLOperacion(response, parametros);

          }

      }
      catch( helpdesk.model.HelpDeskException ex ) {
          Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
          
          HashMap parametros = new HashMap();
          parametros.put("OPERACION_CORRECTA", "false");
          parametros.put("MENSAJE_OPERACION", ex.getMessage());
          this.generarRespuestaXMLOperacion(response, parametros);
          
      }

   }


   /** Edita una tarea registrada en la base de datos
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 5079b59b-1e3b-4593-a8d2-99a3be8674a3 */
   private void editarTarea(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

        try {            
            HttpSession miSesion = request.getSession(false);

            System.out.println("Codificacion: " + request.getCharacterEncoding());           

            String codigo = request.getParameter("hdCodTarea");

            if (codigo == null || codigo.trim().length() <= 0 )
                throw new HelpDeskException("Falta especificar el codigo de tarea");

            String descripcion = request.getParameter("txtDescripcion");
            String valFechaIni = request.getParameter("txtFechaInicio");
            String valFechaFin = request.getParameter("txtFechaFin");

                if ( request.getCharacterEncoding() == null )
                    descripcion = new String(descripcion.getBytes(), "utf-8");
                else if(request.getCharacterEncoding().trim().toLowerCase().equals("utf-8") == false)
                    descripcion = new String(descripcion.getBytes(), "utf-8");

            System.out.println("Cadena: " + descripcion);

            Locale loc = new Locale("es", "PE");

            java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.MEDIUM,
                                                                            java.text.DateFormat.SHORT, loc);

            df.setLenient(false);

            java.util.Date fechaIni = df.parse(valFechaIni);
            java.util.Date fechaFin = df.parse(valFechaFin);

            int codTarea = Integer.parseInt(codigo.trim());
            Tarea tarea = Tarea.getTareaBD(codTarea);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");
            Cargo miCargo = (Cargo) miSesion.getAttribute("sCargo");
            TrabajadorInformatica trabInf = new TrabajadorInformatica(miTrabajador.getUsuario(), miCargo);
            trabInf.editarTarea(tarea, descripcion, fechaIni, fechaFin); 

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "true");
            parametros.put("MENSAJE_OPERACION", "La tarea se registro correctamente");
            
            this.generarRespuestaXMLOperacion(response, parametros);

        } catch (ParseException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", "Formato incorrecto en los datos enviados");

            this.generarRespuestaXMLOperacion(response, parametros);

        } catch (HelpDeskException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", ex.getMessage());

            this.generarRespuestaXMLOperacion(response, parametros);
        }
        catch( Exception ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", "No pudo editarse la tarea");

            this.generarRespuestaXMLOperacion(response, parametros);
        }

   }

   /** Permite registrar un mensaje que es generado durante el proceso de atencion de una solicitud
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid b4c41c05-ab29-4059-a658-4b42d6df251c */
   private void registrarMensaje(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    // TODO: implement
     try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            HttpSession miSesion = request.getSession(false);
            Trabajador miTrabajador = (Trabajador) miSesion.getAttribute("sTrabajador");

            int codSolicitud = 0;
            String asunto = null, descripcion = null, nombreArchivo = null ;
            GestorFichero gf = null ;

            if ( isMultipart == true  ) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

                while ( iter.hasNext() ) {
                FileItem item = (FileItem) iter.next();

                    if (item.isFormField() == true) {

                        if ( item.getFieldName().trim().equals("txtAsunto") ) {
                        asunto = item.getString().trim();
                        }
                        else if ( item.getFieldName().trim().equals("txtDescripcion") ) {
                        descripcion = item.getString().trim() ;
                        }
                        else if ( item.getFieldName().trim().equals("txtArchivo") ) {
                        nombreArchivo = item.getString().trim() ;
                        }
                        else if ( item.getFieldName().trim().equals("hdCodSolic") ) {
                        codSolicitud =  Integer.parseInt(item.getString().trim());
                        }
                        //hdCodSolic
                    }
                    else {

                        if ( item.getSize() > 0 ) {
                        gf = new GestorFichero();
                        gf.guardarFicheroDisco(item, request);                        
                        }

                    }

                }

            }
            else {
            asunto = request.getParameter("txtAsunto").trim();
            descripcion = request.getParameter("txtDescripcion").trim();
            nombreArchivo = request.getParameter("txtArchivo").trim();
            codSolicitud = Integer.parseInt(request.getParameter("hdCodSolic").trim());            
            }

            if ( codSolicitud > 0 ) {

            Solicitud miSolicitud = Solicitud.getSolicitudBD(codSolicitud);
            //int codUltimoTipoTramite = miSolicitud.getUltimoTramite().getTipoTramite().getCodigo();

            Mensaje mensaje = new Mensaje();

            if ( gf != null ) {
            mensaje.setArchivoAdjunto(gf.getNombre());
            mensaje.setTipoContenidoAdjunto(gf.getTipoContenido());
            }

            mensaje.setAsunto(asunto);

            if ( miSolicitud != null && miSolicitud.getUltimoTramite() != null  )
                mensaje.setCodTramite(miSolicitud.getUltimoTramite().getCodTramite());

            mensaje.setDescripcion(descripcion);
            mensaje.setFecRegistro(new java.util.Date());
            mensaje.setNombreAdjunto(nombreArchivo);
            mensaje.setUsuarioOrigen(miTrabajador.getUsuario().trim());

            if ( miTrabajador.getUsuario().trim().toLowerCase().equals(miSolicitud.getTrabajador().getUsuario().toLowerCase().trim()) ) {
            mensaje.setUsuarioDestino(miSolicitud.getUltimoTramite().getTrabajadorOrigen().getUsuario().trim());
            }
            else {
            mensaje.setUsuarioDestino(miSolicitud.getTrabajador().getUsuario().trim());
            }
            
            miTrabajador.guardarMensaje(mensaje);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "true");
            parametros.put("MENSAJE_OPERACION", "La tarea se registro correctamente");

            this.generarRespuestaXMLOperacion(response, parametros);
            
            }
            else {
            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", "La solicitud especificada no existe");

            this.generarRespuestaXMLOperacion(response, parametros);
            }

        } catch (FileUploadException ex) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", "El archivo especificado no existe");
            this.generarRespuestaXMLOperacion(response, parametros);

        } catch (HelpDeskException ex) {

            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            
            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", ex.getMessage());
            this.generarRespuestaXMLOperacion(response, parametros);

         } catch( Exception ex ) {
            Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);

            HashMap parametros = new HashMap();
            parametros.put("OPERACION_CORRECTA", "false");
            parametros.put("MENSAJE_OPERACION", ex.getMessage());
            this.generarRespuestaXMLOperacion(response, parametros);
         }
    
   }

   /** Muestra la pagina de incio en la cual se debe visualizar un resumen de las
    * solicitudes que se estan atendiendo
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 6cf568be-9195-4b65-a7d2-9579c7a07ee1 */
   private void mostrarPaginaInicio(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement      
      HttpSession miSesion = request.getSession(false);
      
      Trabajador miTrab = (Trabajador)miSesion.getAttribute("sTrabajador");
      Cargo miCargo = (Cargo)miSesion.getAttribute("sCargo");

      /*
      Buscador miBusqSolicReg = new Buscador();
      miBusqSolicReg.getSolicRegistradas(miTrab, miCargo);

      Buscador miBusqReqProcAtencion = new Buscador();
      miBusqReqProcAtencion.getMisReqProcesoAtencion(miTrab, miCargo);

      Buscador miBusqReqAsignados = new Buscador();
      miBusqReqAsignados.getMisReqAsignados(miTrab, miCargo);

      Buscador miBusqReqDarConformidad = new Buscador();
      miBusqReqDarConformidad.getMisReqParaConformidad(miTrab, miCargo);

      Buscador miBusqReqDevueltos = new Buscador();
      miBusqReqDevueltos.getMisReqDevueltos(miTrab, miCargo);

      Buscador miBusqReqRechazados = new Buscador();
      miBusqReqRechazados.getMisReqRechazados(miTrab, miCargo);

      Buscador miBusqPendDarVistoBueno = new Buscador();
      miBusqPendDarVistoBueno.getSolicPendVistoBueno(miTrab, miCargo);

      Buscador miBusqPendParaAtender = new Buscador();
      miBusqPendParaAtender.getSolicPendParaAtender(miTrab, miCargo);

      Buscador miBusqPendDerivadas = new Buscador();
      miBusqPendDerivadas.getSolicPendDerivadas(miTrab, miCargo);

      Buscador miBusqPendProcAtencion = new Buscador();
      miBusqPendProcAtencion.getSolicPendProcAtencion(miTrab, miCargo);

      Buscador miBusqPendEnvConformidad = new Buscador();
      miBusqPendEnvConformidad.getSolicPendEnvConformidad(miTrab, miCargo);
      */
      
      FactoryPrototypeBuscador factoryPrototypeBuscador = (FactoryPrototypeBuscador)miSesion.getAttribute("factoryPrototypeBuscador_SESION");
      
      Object ultimaConecion = miSesion.getAttribute("lastConnection_SESION");
      java.util.Date fechaActual = new java.util.Date(); 
      long tiempoActual = fechaActual.getTime();
      
            if (  ultimaConecion == null   ) {
                miSesion.setAttribute("lastConnection_SESION", tiempoActual);
                factoryPrototypeBuscador = new FactoryPrototypeBuscador(miTrab, miCargo);
                miSesion.setAttribute("factoryPrototypeBuscador_SESION", factoryPrototypeBuscador);
            } else {
                long tiempoEnSesion =  (Long)ultimaConecion;
                long diferencia = tiempoActual - tiempoEnSesion;
                
                if ( diferencia > 60000 ) {
                    miSesion.setAttribute("lastConnection_SESION", tiempoActual);
                    factoryPrototypeBuscador = new FactoryPrototypeBuscador(miTrab, miCargo);
                    miSesion.setAttribute("factoryPrototypeBuscador_SESION", factoryPrototypeBuscador);
                }
                
            }

      request.setAttribute("reqRegistradas", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.SOLICITUDES_REGISTRADA));
      request.setAttribute("reqProcAtencion", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.EN_PROCESO_ATENCION_REQUERIMIENTOS));
      request.setAttribute("reqAsignados", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.ASIGNADAS));
      request.setAttribute("reqDarConformidad", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.ENVIAR_PARA_CONFORMIDAD));
      request.setAttribute("reqDevueltos", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.DEVUELTAS));
      request.setAttribute("reqRechazados", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.RECHAZADAS));

      request.setAttribute("pendDarVistoBueno", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.DAR_VISTO_BUENO));
      request.setAttribute("pendParaAtender", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.PARA_ATENDER));
      request.setAttribute("pendDerivadas", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.DERIVADAS));
      request.setAttribute("pendProcAtencion", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.EN_PROCESO_ATENCION_PENDIENTES));
      request.setAttribute("pendEnviarConformidad", factoryPrototypeBuscador.getPrototypeBuscador(TipoDeBusqueda.PARA_DAR_CONFORMIDAD));
      
      request.getRequestDispatcher("inicio.jsp").forward(request, response);
   }

   /** Permite cambiar la clave del trabajador
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid 65590855-40f1-4fc4-8a02-f503643288c8 */
   private void cambiarClave(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
      // TODO: implement

      try {

     Thread.sleep(800);

      HttpSession sesion = request.getSession(false);

      Trabajador trab = (Trabajador)sesion.getAttribute("sTrabajador");
      String claveAnt = request.getParameter("claveAnterior");
      String claveNueva = request.getParameter("claveNueva");

      Login log = new Login(trab.getUsuario());
      log.setClave(claveAnt);
      log.cambiarClave(claveNueva);

      HashMap parametros = new HashMap();
      parametros.put("OPERACION_CORRECTA", "true");
      parametros.put("MENSAJE_OPERACION", "La clave fue cambiada correctamente");
      this.generarRespuestaXMLOperacion(response, parametros);

      }
      catch( Exception ex ) {
      Logger.getLogger(ControlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
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
    * @pdOid f77a8f1a-b697-4e0e-bd14-4ec119833f31 */
   @Override
   public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
           throws javax.servlet.ServletException, java.io.IOException {
       
   String opcion = request.getParameter("opcion");
   HttpSession miSesion = request.getSession(false);

        if ( opcion != null && miSesion != null ) {

            if ( opcion.trim().equals("_misReqVerSolicReg") )
                mostrarSolicRegistradas(request, response);

            if ( opcion.trim().equals("_misReqVerSolicProcAtencion") ||
                 opcion.trim().equals("_misReqVerSolicAsignadas") ||
                 opcion.trim().equals("_misReqVerSolicDarConformidad") ||
                 opcion.trim().equals("_misReqVerSolicDevueltas")  ||
                 opcion.trim().equals("_misReqVerSolicRechazadas") )    {
                this.mostrarMisRequerimientos(request, response, opcion);
            }

            if ( opcion.trim().equals("_verSolicAtenVB") )
                mostrarSolicAtenderVB(request, response);

            if ( opcion.trim().equals("_verSolicPorAtender") )
                mostrarSolicPorAtender(request, response);

            if ( opcion.trim().equals("_verSolicDerivadas") ||
                    opcion.trim().equals("_verSolicProcAtencion") || 
                    opcion.trim().equals("_verSolicEnvConformidad") )                
                mostrarMisPendientes(request, response, opcion);
            
            if ( opcion.trim().equals("_verPaginaInicio") )
                mostrarPaginaInicio(request, response);
            
        }

   }

   /** Called by the server (via the service method) to allow a servlet to handle a POST request.
    *
    * @param request
    * @param response
    * @exception javax.servlet.ServletException
    * @exception java.io.IOException
    * @pdOid ada5c94f-ad3d-48d5-9b80-c1e0f1830875 */
   @Override
   public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
           throws javax.servlet.ServletException, java.io.IOException {

    String opcion=request.getParameter("opcion");
    HttpSession miSesion = request.getSession(false);
    
        if ( opcion != null && miSesion != null ) {

                if ( opcion.trim().equals("_darVistoBueno") )
                    darVistoBueno(request, response);
                
                if ( opcion.trim().equals("_atenderSolicitud") )
                    atenderSolicitud(request, response);

                if ( opcion.trim().equals("_derivarSolicitud") )
                    derivarSolicitud(request, response);

                if ( opcion.trim().equals("_devolverSolicitud") )
                    devolverSolicitud(request, response);
                
                if ( opcion.trim().equals("_rechazarSolicitud") )
                    rechazarSolicitud(request, response);

                if ( opcion.trim().equals("_reenviarSolicitud") )
                    reenviarSolicitud(request, response);

                if ( opcion.trim().equals("_cerrarSolicitud") )
                    cerrarSolicitud(request, response);                
                
                if ( opcion.trim().equals("_devolverSolicConformidad") )
                    devolverSolicConformidad(request, response);
                
                if ( opcion.trim().equals("_atenderSolicitudDerivada") )
                    atenderSolicitudDerivada(request, response);

                if ( opcion.trim().equals("_finalizarAtencionSolicitud") )
                    finalizarAtencionSolicitud(request, response);
                
/*                if ( opcion.trim().equals("_finalizarAtencionSolicitud") )
                    finalizarAtencionSolicitud(request, response);*/
                
                if ( opcion.trim().equals("_enviarParaConformidadSolicitud") )
                    enviarParaConformidadSolicitud(request, response);

                if ( opcion.trim().equals("_devolverSolicitudFinalizada") )
                    devolverSolicitudFinalizada(request, response);

                if ( opcion.trim().equals("_registrarTarea") )
                    registrarTarea(request, response);

                if ( opcion.trim().equals("_eliminarTarea") )
                    eliminarTarea(request, response);

                if ( opcion.trim().equals("_editarTarea") )
                    editarTarea(request, response);

                if ( opcion.trim().equals("_registrarMensaje") )
                    registrarMensaje(request, response);

                if ( opcion.trim().equals("_cambiarClave") )
                    cambiarClave(request, response);

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