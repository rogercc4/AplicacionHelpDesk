<%-- 
    Document   : detailMiReqDevuelto
    Created on : 23/08/2011, 12:19:20 AM
    Author     : Roger
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitudes devueltas ( Detalle de la Solicitud )</title>
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
							
				$('button').click( function() {					
				var $boton = $(this); 
				var idBoton = jQuery.trim($boton.attr('id')); 
				var urlDestino ; 
					
					if ( idBoton == "reenviarSolicitud" ) {
					urlDestino = "controlSolicitud.do?opcion=_verFormReenviarSolicitud&"; 
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
        <h3>Solicitudes devueltas ( Detalle de la Solicitud ) </h3>
        <jsp:include page="detailSolicitud.inc.jsp"/>        
    <table width="100%" >
          <tr>
            <td>            
            <button type="button" id="reenviarSolicitud" >
            <span>Re-Enviar Solicitud ...</span>
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
