<%-- 
    Document   : detailSolicPorAtender
    Created on : 07/08/2011, 11:10:03 PM
    Author     : Roger
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalle de solicitud por atender</title>
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
				
				if ( jQuery.trim($boton.attr('id')) == "derivarSolicitud" ) {
				urlDestino = "controlSolicitud.do?opcion=_verFormDerivarSolic&" ; 
				urlDestino += "codSolic=${requestScope.miSolicitud.codSolicitud}&"; 
				urlDestino += "txtPage=${requestScope.numPage}"; 
				document.location.href = urlDestino ; 
				}
				else if ( jQuery.trim($boton.attr('id')) == "devolverSolicitud" ) {
				urlDestino = "controlSolicitud.do?opcion=_verFormDevolverSolic&" ; 
				urlDestino += "codSolic=${requestScope.miSolicitud.codSolicitud}&" ; 
				urlDestino += "txtPage=${requestScope.numPage}&opcionReturn=_detalleSolicPorAtender"; 
              	document.location.href = urlDestino ; 
				}
				else if ( jQuery.trim($boton.attr('id')) == "regresarLista" ) {
				urlDestino = "controlTrabajador.do?opcion=_verSolicPorAtender&" ; 
				urlDestino += "txtPage=${requestScope.numPage}"; 
				document.location.href = urlDestino ; 
				}
				else if ( jQuery.trim($boton.attr('id')) == "rechazarSolicitud" ) {
				urlDestino = "controlSolicitud.do?";   
				urlDestino += "opcion=_verFormRechazarSolic&"; 
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
        <h3>Para Atender ( Detalle de Solicitud ) </h3>
        <jsp:include page="detailSolicitud.inc.jsp"/>
        <br/>
        <table >
          <tr>
            <td>
            <button type="button" id="derivarSolicitud">
            <span>Derivar Solicitud...</span>
            </button>
            
            &nbsp;&nbsp;
            <button type="button" id="devolverSolicitud">
            <span>Devolver Solicitud...</span>
            </button>
            
            &nbsp;&nbsp;
            <button type="button" id="rechazarSolicitud">
            <span>Rechazar Solicitud ...</span>
            </button>
            
            &nbsp;&nbsp;
            <button type="button" id="imprimirSolicitud">
            <span>Imprimir</span>
            </button>
            
		</td>
          </tr>
        </table>
      <br/>
    </div>
    </body>
</html>
