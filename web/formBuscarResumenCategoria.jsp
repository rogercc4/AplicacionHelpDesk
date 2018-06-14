<%-- 
    Document   : formBuscarResumenCategoria
    Created on : 13/10/2011, 11:06:08 AM
    Author     : rcontreras
--%>

<jsp:include page="verificarSesion.inc.jsp"></jsp:include>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscar por clasificacion</title>
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
        <script type="text/javascript" src="lib_java/jquery/plugin/jQuery.form.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.datepicker.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.ui.datepicker-es.js"></script>       
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.effects.core.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.effects.blind.min.js"></script>
        <script type="text/javascript" src="lib_java/jquery/ui/jquery.effects.blind.min.js"></script>
        <script type="text/javascript" src="lib_java/highcharts-2.1.6/highcharts.js"></script>
        <script type="text/javascript" src="lib_java/highcharts-2.1.6/modules/exporting.js"></script>
        <script type="text/javascript" src="lib_java/formBuscarResumenCategoria.js"></script>
		<style>
                .ui-datepicker {
                font-size: 11px;
                }
		.mensajeTrError {
		font-size: 12px;
		font-family: Verdana, Arial, Helvetica, sans-serif;
		font-weight: bold;
		color: #FFFFFF;
		padding: 10px;
		}		
		#tabsConsultarMisReq li .ui-icon-close { 
		float: left; margin: 0.4em 0.2em 0 0; cursor: pointer; 
		}		
                </style>
    </head>
    <body>
    <div id="parteDetalle">
    <div id="tabsConsultarMisReq">
        
        <ul >
        <li ><a href="#tabs-1">Consultar Mis Requerimientos</a></li>
        </ul>
        
        <div id="tabs-1">   
        <form action="" method="post" name="formBusqMisReq" id="formBusqMisReq">
      <table width="100%" border="0" cellpadding="2" cellspacing="0">                
        <tr class="ui-state-error">
          <td colspan="4" > <span class="mensajeTrError">No se han encontrado los datos buscados</span></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="3">&nbsp;</td>
        </tr>                
        <tr>
          <td><strong>Fecha Inicio</strong></td>
          <td width="30%"><label>
            <input name="txtFechaInicio" type="text" id="txtFechaInicio" size="15" title="Fecha de inicio">
          </label></td>
          <td width="13%"><strong>Fecha Fin</strong></td>
          <td width="42%"><input name="txtFechaFin" type="text" id="txtFechaFin" size="15" title="Fecha de fin"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="3">
          <button id="btnBuscar" type="button">
          Buscar Solicitudes          </button>          </td>
        </tr>
      </table>
    </form>
        </div>
        </div>
        
        
        <div id="resultadosBusqueda" ></div>
        
    </div>
        
	<div id="dialogMensaje" class="mensajeDialogo"></div>
            
</body>
</html>
