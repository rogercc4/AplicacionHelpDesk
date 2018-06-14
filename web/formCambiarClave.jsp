<%-- 
    Document   : formCambiarClave
    Created on : 06/10/2011, 09:19:34 AM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cambiar Clave</title>
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
        <script type="text/javascript" src="lib_java/cambiarClave.js"></script>
        </head>
    <body>
        <div id="tabsDetalleSolicitud">
        
        <ul >
        <li ><a href="#tabs-1">Cambiar Clave</a></li>
        </ul>
        
        <div id="tabs-1">   
        	<form name="formCambiarClave" method="post" action="">            
        	  <table width="100%" border="0" cellspacing="0" cellpadding="3">
              <tr>
                <td colspan="2" id="tdMensajeSistema">
                  <div class="ui-state-error">
                	<strong>Falta completar el campo:                  </strong>                    </div>                    </td>
                </tr>
                <tr>
                  <td width="18%"><strong>Usuario</strong></td>
                  <td><label>
                    <input name="txtUsuario" type="text" id="txtUsuario" title="Nombre de Usuario" value="${sessionScope.sTrabajador.usuario}  " size="40" readonly="true" >
                  </label></td>
                </tr>
                <tr>
                  <td><strong>Clave Actual</strong></td>
                  <td>
                  <input name="txtClaveAnt" type="password" 
                  	id="txtClaveAnt" title="Clave actual" size="40">					</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td><strong>Clave Nueva</strong></td>
                  <td>
                  <input name="txtClaveNueva1" type="password" 
                  	id="txtClaveNueva1" title="Clave Nueva" size="40">                  </td>
                </tr>
                <tr>
                  <td><strong>Repetir nueva clave</strong></td>
                  <td>
                  <input name="txtClaveNueva2" type="password" 
                  	id="txtClaveNueva2" title="Repetir nueva clave" size="40">                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                  <button id="btnCambiarClave" type="button">
                  Cambiar Clave                  </button>
                  
                       
                  <button id="btnCancelar" type="button">
                  Cancelar                  </button>                  </td>
                </tr>
              </table>
          </form>
        
          </div>
        
    </div>
    
    <div id="dialogVentana" class="mensajeDialogo">    
    </div>
    <div id="dialogProgreso" class="mensajeDialogo">
    </div>
    </body>
</html>
