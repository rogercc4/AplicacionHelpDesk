<%-- 
    Document   : detailSolicDarConformidad
    Created on : 22/08/2011, 09:38:45 AM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dar Conformidad de usuario</title>
            <link rel="stylesheet" type="text/css" href="css/estilos.css" />
            <link rel="stylesheet" type="text/css" href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css">
            <script type="text/javascript" src="lib_java/funciones.js"></script>
            <script type="text/javascript" src="lib_java/jquery/jquery.js"></script>
            <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.core.min.js"></script>
            <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.widget.min.js"></script>
            <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.button.min.js"></script>            
            <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.tabs.min.js"></script>
            <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.mouse.min.js"></script>
            <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.draggable.min.js"></script>
            <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.position.min.js"></script>
            <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.dialog.min.js"></script>
			<script type="text/javascript">
            $(document).ready( function() {		
            $('button').button(); 
                
                $('button').click( function(evento) {
                $boton = $(this); 
                var urlDestino = ""; 
                    
                    if (  jQuery.trim($boton.attr('id')) == "cerrarSolicitud" ) {
                    urlDestino = "controlSolicitud.do?opcion=_verFormCerrarSolic&"; 
                    urlDestino += "codSolic=${requestScope.miSolicitud.codSolicitud}"; 
                    document.location.href = urlDestino; 
                    }
                    else if ( jQuery.trim($boton.attr('id')) == "devolverSolicitud" ) {
                    urlDestino = "controlSolicitud.do?opcion=_verFormDevolverConformidad&"; 
                    urlDestino += "codSolic=${requestScope.miSolicitud.codSolicitud}"; 
                    document.location.href = urlDestino ; 
                    }
                    else {
                    return false; 
                    }
                
                });
            
            }); 
            </script>
        
        
    </head>
    <body>
    
    <div id="parteDetalle">
        <h3>Dar conformidad a solicitud ( Detalle de la Solicitud ) </h3>
        <jsp:include page="detailSolicitud.inc.jsp"/>
        <table width="100%" >
          <tr>
            <td>
            <button type="button" id="cerrarSolicitud" >
            <span>Cerrar Solicitud...</span>
            </button>            
            
            &nbsp;&nbsp;  
            <button type="button" id="devolverSolicitud" >
            <span>Devolver Solicitud...</span>
            </button>
          </tr>
          <tr>
          <td>
          </td>
          </tr>
        </table>        
      <br/>
    </div>

    </body>
</html>