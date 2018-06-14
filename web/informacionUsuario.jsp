<%-- 
    Document   : informacionUsuario
    Created on : 07/10/2011, 04:19:44 PM
    Author     : rcontreras
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Informacion de usuario</title>
        <link rel="stylesheet" type="text/css" href="css/estilos.css" />		
		<link rel="stylesheet" type="text/css" href="lib_java/jquery/themes/ui-lightness/jquery.ui.all.css">
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
		$(document).ready(function() {
		$('div#tabsDetalleSolicitud').tabs(); 
		}); 
		</script>        
        
    </head>
    <body>
        <div id="tabsDetalleSolicitud">
        
        <ul >
        <li ><a href="#tabs-1">Informacion Usuario</a></li>
        </ul>
        
        <div id="tabs-1">   
        <table width="100%" border="0" cellpadding="3" cellspacing="0">
      <tr>
        <td colspan="2"><strong>Usuario:</strong></td>
        <td>${sessionScope.sTrabajador.usuario}</td>
      </tr>
      <tr>
        <td colspan="2"><strong>Nombres:</strong></td>
        <td width="85%">${sessionScope.sTrabajador.nombre}</td>
      </tr>
      <tr>
        <td colspan="2"><strong>Apellidos:</strong></td>
        <td>${sessionScope.sTrabajador.apellido}</td>
      </tr>
      <tr>
        <td colspan="2"><strong>Cargo:</strong></td>
        <td>${sessionScope.sTrabajador.cargo.nombre}</td>
      </tr>
      <tr>
        <td colspan="2"><strong>Cargo login:</strong></td>
        <td>${sessionScope.sCargo.nombre}</td>
      </tr>
      <tr>
        <td colspan="2"><strong>Area:</strong></td>
        <td>${sessionScope.sTrabajador.cargo.area.nombre}</td>
      </tr>
      <tr>
        <td colspan="2"><strong>DNI:</strong></td>
        <td>${sessionScope.sTrabajador.dni}</td>
      </tr>
      <tr>
        <td colspan="2"><strong>Correo:</strong></td>
        <td>${sessionScope.sTrabajador.correo}</td>
      </tr>      
    </table>
        </div>  
          
    </div>	
</body>
</html>
