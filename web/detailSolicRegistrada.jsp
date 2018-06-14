<%-- 
    Document   : detailSolicRegistrada
    Created on : 30/07/2011, 06:15:27 PM
    Author     : Roger
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalle de Solicitud Registrada</title>        
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
			
				$('button#imprimirSolicitud').click(function(){
				var url = "controlReporte.do?_callReporte=mostrarRptDetalleSolicitud&"; 
				url += "codSolic=${requestScope.miSolicitud.codSolicitud}"; 
				window.open(url);
				});
			
										
			});  
			</script>
            
    </head>
    <body>
    <div id="parteDetalle">
        <h3>Detalle de Solicitud Registrada</h3>
        <jsp:include page="detailSolicitud.inc.jsp"/>        
        <br/>
        <table >
          <tr>
            <td>
            <button type="button" id="imprimirSolicitud" >
            <span>imprimir</span>
            </button>            
            </td>
          </tr>
        </table>
      <br/>
    </div>	
    </body>
</html>