<%-- 
    Document   : detailSolicProcAtencion
    Created on : 26/08/2011, 01:23:45 PM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitud en proceso de atencion</title>
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
			$(function() {
			$('button').button();
			
			$('button').click( function() {
			var $boton = $(this); 
			var urlDestino = "";
				
				if ( jQuery.trim($boton.attr('id')) == "gestionarTareas" ) {
				urlDestino = "controlSolicitud.do?opcion=_verFormGestionarTareas&" ; 
				urlDestino += "codSolic=${requestScope.miSolicitud.codSolicitud}" ; 
				document.location.href = urlDestino ;
				}
				else if ( jQuery.trim($boton.attr('id')) == "gestionarMensajes" ) {
				urlDestino = "controlSolicitud.do?opcion=_verFormGestionarMensajes&" ; 
				urlDestino += "codSolic=${requestScope.miSolicitud.codSolicitud}" ; 
				document.location.href = urlDestino ;				
				}
				else if ( jQuery.trim($boton.attr('id')) == "devolverSolicitud" ) {
				urlDestino = "controlSolicitud.do?opcion=_verFormDevolverSolic&" ; 
				urlDestino += "codSolic=${requestScope.miSolicitud.codSolicitud}"; 
              	document.location.href = urlDestino ; 
				}
				else if ( jQuery.trim($boton.attr('id')) == "finalizarAtencion" ) {
				urlDestino = "controlSolicitud.do?opcion=_verFormFinalizarAtencion&" ; 
				urlDestino += "codSolic=${requestScope.miSolicitud.codSolicitud}"; 
              	document.location.href = urlDestino ; 
				}
				else  {
				return false ; 
				}
			
			}); 
			
			}); 
			</script>                    
        
    </head>
    <body>
        <div id="parteDetalle">
        <h3>En  proceso de atencion ( Detalle de Solicitud ) </h3>
        <jsp:include page="detailSolicitud.inc.jsp"/>
        <br/>
        <table >
          <tr>
            <td>
            <button type="button" id="gestionarTareas">
            <span>Gestionar tareas...</span>
            </button>
            
            &nbsp;&nbsp;
            <button type="button" id="gestionarMensajes">
            <span>Gestionar  mensajes...</span>
            </button>
            
			&nbsp;&nbsp;  
            <button type="button" id="devolverSolicitud">
            <span>Devolver...</span>
            </button>
            
			&nbsp;&nbsp;  
            <button type="button" id="finalizarAtencion">
            <span>Finalizar atencion...</span>
            </button>
            </td>
          </tr>
        </table>
      <br/>
    </div>
    
    </body>
</html>